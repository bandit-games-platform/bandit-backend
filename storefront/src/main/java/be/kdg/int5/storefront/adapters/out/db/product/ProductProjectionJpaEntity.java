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
    private BigDecimal price;

    public ProductProjectionJpaEntity() {
    }

    public ProductProjectionJpaEntity(UUID id, String title, BigDecimal price) {
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
