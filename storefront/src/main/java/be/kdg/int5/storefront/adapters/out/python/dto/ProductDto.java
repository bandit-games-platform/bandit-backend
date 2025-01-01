package be.kdg.int5.storefront.adapters.out.python.dto;

import java.util.UUID;

public class ProductDto {
    private UUID id;
    private String title;
    private UUID developerId;
    private String description;

    public ProductDto() {
    }

    public ProductDto(UUID id, String title, UUID developerId, String description) {
        this.id = id;
        this.title = title;
        this.developerId = developerId;
        this.description = description;
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
}
