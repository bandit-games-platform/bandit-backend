package be.kdg.int5.storefront.adapters.in.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {
    private UUID id;
    private String title;
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(UUID id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
