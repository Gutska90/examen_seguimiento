package com.shipping.service;

import com.shipping.dto.ShipmentRequest;
import com.shipping.dto.ShipmentResponse;
import com.shipping.exception.DuplicateResourceException;
import com.shipping.exception.ResourceNotFoundException;
import com.shipping.model.Shipment;
import com.shipping.model.ShipmentStatus;
import com.shipping.repository.ShipmentRepository;
import com.shipping.service.impl.ShipmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    private ShipmentRequest validRequest;
    private Shipment sampleShipment;

    @BeforeEach
    void setUp() {
        validRequest = new ShipmentRequest();
        validRequest.setTrackingNumber("AB123456789CD");
        validRequest.setOriginAddress("Calle Origen 123");
        validRequest.setDestinationAddress("Calle Destino 456");
        validRequest.setStatus(ShipmentStatus.REGISTERED);

        sampleShipment = new Shipment();
        sampleShipment.setId(1L);
        sampleShipment.setTrackingNumber("AB123456789CD");
        sampleShipment.setOriginAddress("Calle Origen 123");
        sampleShipment.setDestinationAddress("Calle Destino 456");
        sampleShipment.setStatus(ShipmentStatus.REGISTERED);
    }

    @Test
    void createShipment_Success() {
        when(shipmentRepository.existsByTrackingNumber(anyString())).thenReturn(false);
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(sampleShipment);

        ShipmentResponse response = shipmentService.createShipment(validRequest);

        assertNotNull(response);
        assertEquals(validRequest.getTrackingNumber(), response.getTrackingNumber());
        verify(shipmentRepository).save(any(Shipment.class));
    }

    @Test
    void createShipment_DuplicateTrackingNumber() {
        when(shipmentRepository.existsByTrackingNumber(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> 
            shipmentService.createShipment(validRequest)
        );
        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    void getShipmentByTrackingNumber_Success() {
        when(shipmentRepository.findByTrackingNumber(anyString()))
            .thenReturn(Optional.of(sampleShipment));

        ShipmentResponse response = shipmentService.getShipmentByTrackingNumber("AB123456789CD");

        assertNotNull(response);
        assertEquals(sampleShipment.getTrackingNumber(), response.getTrackingNumber());
    }

    @Test
    void getShipmentByTrackingNumber_NotFound() {
        when(shipmentRepository.findByTrackingNumber(anyString()))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            shipmentService.getShipmentByTrackingNumber("AB123456789CD")
        );
    }

    @Test
    void updateShipment_Success() {
        when(shipmentRepository.findByTrackingNumber(anyString()))
            .thenReturn(Optional.of(sampleShipment));
        when(shipmentRepository.existsByTrackingNumber(anyString())).thenReturn(false);
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(sampleShipment);

        ShipmentResponse response = shipmentService.updateShipment("AB123456789CD", validRequest);

        assertNotNull(response);
        assertEquals(validRequest.getTrackingNumber(), response.getTrackingNumber());
        verify(shipmentRepository).save(any(Shipment.class));
    }

    @Test
    void getAllShipments_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Shipment> shipmentPage = new PageImpl<>(List.of(sampleShipment));
        
        when(shipmentRepository.findAll(pageable)).thenReturn(shipmentPage);

        Page<ShipmentResponse> response = shipmentService.getAllShipments(pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(sampleShipment.getTrackingNumber(), 
            response.getContent().get(0).getTrackingNumber());
    }

    @Test
    void deleteShipment_Success() {
        when(shipmentRepository.findByTrackingNumber(anyString()))
            .thenReturn(Optional.of(sampleShipment));
        doNothing().when(shipmentRepository).delete(any(Shipment.class));

        assertDoesNotThrow(() -> shipmentService.deleteShipment("AB123456789CD"));
        verify(shipmentRepository).delete(any(Shipment.class));
    }

    @Test
    void deleteShipment_NotFound() {
        when(shipmentRepository.findByTrackingNumber(anyString()))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            shipmentService.deleteShipment("AB123456789CD")
        );
        verify(shipmentRepository, never()).delete(any(Shipment.class));
    }
} 