package be.kdg.int5.storefront.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductProjection {
    private final ProductId productId;
    private String title;
    private final UUID developerId;
    private String description;
    private BigDecimal price;
    private String backgroundUrl;

    public ProductProjection(ProductId productId, String title, UUID developerId, BigDecimal price) {
        this(productId, title, developerId, null, price, null);
    }

    public ProductProjection(
            ProductId productId,
            String title,
            UUID developerId,
            String description,
            BigDecimal price,
            String backgroundUrl) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(title);
        Objects.requireNonNull(developerId);
        Objects.requireNonNull(description);
        Objects.requireNonNull(price);
        Objects.requireNonNull(backgroundUrl);

        this.productId = productId;
        this.title = title;
        this.developerId = developerId;
        this.description = description;
        this.price = price;
        this.backgroundUrl = backgroundUrl;
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

    public UUID getDeveloperId() {
        return developerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }
}
