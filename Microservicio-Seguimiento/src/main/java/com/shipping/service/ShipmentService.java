package com.shipping.service;

import com.shipping.dto.ShipmentRequest;
import com.shipping.dto.ShipmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShipmentService {
    ShipmentResponse createShipment(ShipmentRequest request);
    ShipmentResponse getShipmentByTrackingNumber(String trackingNumber);
    ShipmentResponse updateShipment(String trackingNumber, ShipmentRequest request);
    Page<ShipmentResponse> getAllShipments(Pageable pageable);
    void deleteShipment(String trackingNumber);
} 