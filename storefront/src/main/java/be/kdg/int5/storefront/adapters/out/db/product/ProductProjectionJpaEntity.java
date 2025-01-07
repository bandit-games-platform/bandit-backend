package be.kdg.int5.storefront.adapters.out.db.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "storefront", name = "product")
public class ProductProjectionJpaEntity {
    @Id
    private UUID id;
    private String title;
    private UUID developerId;
    private String description;
    private BigDecimal price;
    private String backgroundUrl;

    public ProductProjectionJpaEntity() {
    }

    public ProductProjectionJpaEntity(
            UUID id,
            String title,
            UUID developerId,
            String description,
            BigDecimal price,
            String backgroundUrl) {
        this.id = id;
        this.title = title;
        this.developerId = developerId;
        this.description = description;
        this.price = price;
        this.backgroundUrl = backgroundUrl;
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

    public UUID getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(UUID developerId) {
        this.developerId = developerId;
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
