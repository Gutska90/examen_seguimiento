package com.shipping.dto;

import com.shipping.model.ShipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShipmentRequest {
    
    @NotBlank(message = "El número de seguimiento es obligatorio")
    @Pattern(regexp = "^[A-Z]{2}\\d{9}[A-Z]{2}$", message = "El formato del número de seguimiento debe ser: 2 letras + 9 dígitos + 2 letras")
    private String trackingNumber;

    @NotBlank(message = "La dirección de origen es obligatoria")
    private String originAddress;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String destinationAddress;

    @NotNull(message = "El estado del envío es obligatorio")
    private ShipmentStatus status;

    private String currentLocation;
    private String estimatedDeliveryDate;
    private String recipientName;
    private String recipientPhone;
} 