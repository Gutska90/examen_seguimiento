package com.shipping.dto;

import com.shipping.model.ShipmentStatus;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Data
@Relation(collectionRelation = "shipments", itemRelation = "shipment")
public class ShipmentResponse extends RepresentationModel<ShipmentResponse> {
    private Long id;
    private String trackingNumber;
    private String originAddress;
    private String destinationAddress;
    private ShipmentStatus status;
    private String currentLocation;
    private String estimatedDeliveryDate;
    private String recipientName;
    private String recipientPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 