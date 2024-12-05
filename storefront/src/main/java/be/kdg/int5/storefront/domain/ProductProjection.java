package be.kdg.int5.storefront.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductProjection {
    private final ProductId productId;
    private String title;
    private BigDecimal price;

    public ProductProjection(ProductId productId, String title, BigDecimal price) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(title);
        Objects.requireNonNull(price);

        this.productId = productId;
        this.title = title;
        this.price = price;
    }

    public ProductId getProductId() {
        return productId;
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
