package com.shipping.controller;

import com.shipping.dto.ShipmentRequest;
import com.shipping.dto.ShipmentResponse;
import com.shipping.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ShipmentResponse> createShipment(@Valid @RequestBody ShipmentRequest request) {
        ShipmentResponse response = shipmentService.createShipment(request);
        response.add(linkTo(methodOn(ShipmentController.class).getShipmentByTrackingNumber(response.getTrackingNumber())).withSelfRel());
        response.add(linkTo(methodOn(ShipmentController.class).getAllShipments(Pageable.unpaged())).withRel(IanaLinkRelations.COLLECTION));
        
        return ResponseEntity.created(linkTo(methodOn(ShipmentController.class)
                .getShipmentByTrackingNumber(response.getTrackingNumber())).toUri())
                .body(response);
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ShipmentResponse> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        ShipmentResponse response = shipmentService.getShipmentByTrackingNumber(trackingNumber);
        response.add(linkTo(methodOn(ShipmentController.class).getShipmentByTrackingNumber(trackingNumber)).withSelfRel());
        response.add(linkTo(methodOn(ShipmentController.class).getAllShipments(Pageable.unpaged())).withRel(IanaLinkRelations.COLLECTION));
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{trackingNumber}")
    public ResponseEntity<ShipmentResponse> updateShipment(
            @PathVariable String trackingNumber,
            @Valid @RequestBody ShipmentRequest request) {
        ShipmentResponse response = shipmentService.updateShipment(trackingNumber, request);
        response.add(linkTo(methodOn(ShipmentController.class).getShipmentByTrackingNumber(response.getTrackingNumber())).withSelfRel());
        response.add(linkTo(methodOn(ShipmentController.class).getAllShipments(Pageable.unpaged())).withRel(IanaLinkRelations.COLLECTION));
        
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ShipmentResponse>> getAllShipments(Pageable pageable) {
        Page<ShipmentResponse> shipments = shipmentService.getAllShipments(pageable);
        shipments.forEach(shipment -> {
            shipment.add(linkTo(methodOn(ShipmentController.class)
                    .getShipmentByTrackingNumber(shipment.getTrackingNumber())).withSelfRel());
        });
        
        return ResponseEntity.ok(shipments);
    }

    @DeleteMapping("/{trackingNumber}")
    public ResponseEntity<Void> deleteShipment(@PathVariable String trackingNumber) {
        shipmentService.deleteShipment(trackingNumber);
        return ResponseEntity.noContent().build();
    }
} 