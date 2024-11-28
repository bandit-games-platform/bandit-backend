package be.kdg.int5.storefront.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class NewOrderDto {
    @NotNull
    private UUID productId;

    public NewOrderDto() {
    }

    public NewOrderDto(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
