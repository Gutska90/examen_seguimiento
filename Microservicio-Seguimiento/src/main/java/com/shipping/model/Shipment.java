package com.shipping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shipments")
public class Shipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_seq")
    @SequenceGenerator(name = "shipment_seq", sequenceName = "shipment_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "El número de seguimiento es obligatorio")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[A-Z]{2}\\d{9}[A-Z]{2}$", message = "El formato del número de seguimiento debe ser: 2 letras + 9 dígitos + 2 letras")
    private String trackingNumber;

    @NotBlank(message = "La dirección de origen es obligatoria")
    @Column(nullable = false)
    private String originAddress;

    @NotBlank(message = "La dirección de destino es obligatoria")
    @Column(nullable = false)
    private String destinationAddress;

    @NotNull(message = "El estado del envío es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column
    private String currentLocation;

    @Column
    private String estimatedDeliveryDate;

    @Column
    private String recipientName;

    @Column
    private String recipientPhone;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
} 