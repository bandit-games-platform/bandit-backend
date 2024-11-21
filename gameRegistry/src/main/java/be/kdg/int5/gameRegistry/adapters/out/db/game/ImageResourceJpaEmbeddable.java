package be.kdg.int5.gameRegistry.adapters.out.db.game;

import jakarta.persistence.Embeddable;

@Embeddable
public class ImageResourceJpaEmbeddable {
    private String url;

    public ImageResourceJpaEmbeddable() {
    }

    public ImageResourceJpaEmbeddable(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
