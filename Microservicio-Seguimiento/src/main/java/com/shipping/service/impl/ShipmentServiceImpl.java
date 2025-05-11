package com.shipping.service.impl;

import com.shipping.dto.ShipmentRequest;
import com.shipping.dto.ShipmentResponse;
import com.shipping.exception.ResourceNotFoundException;
import com.shipping.exception.DuplicateResourceException;
import com.shipping.model.Shipment;
import com.shipping.repository.ShipmentRepository;
import com.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Override
    @Transactional
    public ShipmentResponse createShipment(ShipmentRequest request) {
        if (shipmentRepository.existsByTrackingNumber(request.getTrackingNumber())) {
            throw new DuplicateResourceException("Ya existe un envío con el número de seguimiento: " + request.getTrackingNumber());
        }

        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(request, shipment);
        shipment = shipmentRepository.save(shipment);
        
        return convertToResponse(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public ShipmentResponse getShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con número de seguimiento: " + trackingNumber));
        
        return convertToResponse(shipment);
    }

    @Override
    @Transactional
    public ShipmentResponse updateShipment(String trackingNumber, ShipmentRequest request) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con número de seguimiento: " + trackingNumber));

        // Validar que el nuevo tracking number no exista si se está cambiando
        if (!trackingNumber.equals(request.getTrackingNumber()) && 
            shipmentRepository.existsByTrackingNumber(request.getTrackingNumber())) {
            throw new DuplicateResourceException("Ya existe un envío con el número de seguimiento: " + request.getTrackingNumber());
        }

        BeanUtils.copyProperties(request, shipment);
        shipment = shipmentRepository.save(shipment);
        
        return convertToResponse(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentResponse> getAllShipments(Pageable pageable) {
        return shipmentRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    @Override
    @Transactional
    public void deleteShipment(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con número de seguimiento: " + trackingNumber));
        
        shipmentRepository.delete(shipment);
    }

    private ShipmentResponse convertToResponse(Shipment shipment) {
        ShipmentResponse response = new ShipmentResponse();
        BeanUtils.copyProperties(shipment, response);
        return response;
    }
} 