package com.shipping.model;

public enum ShipmentStatus {
    REGISTERED("Registrado"),
    IN_TRANSIT("En tránsito"),
    ARRIVED_AT_DESTINATION("Llegado a destino"),
    OUT_FOR_DELIVERY("En reparto"),
    DELIVERED("Entregado"),
    RETURNED("Devuelto"),
    EXCEPTION("Excepción");

    private final String description;

    ShipmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 