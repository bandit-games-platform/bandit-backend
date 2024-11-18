package be.kdg.int5.gameRegistry.adapters.out.db.game;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "game_registry", name = "image_resource")
public class ImageResourceJpaEntity {
    @Id
    private String url;

    public ImageResourceJpaEntity() {
    }

    public ImageResourceJpaEntity(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
