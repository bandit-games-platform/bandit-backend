package be.kdg.int5.storefront.adapters.in.dto;

import java.util.UUID;

public class CreatedOrderIdDto {
    private UUID orderId;

    public CreatedOrderIdDto() {
    }

    public CreatedOrderIdDto(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
