package be.kdg.int5.gameRegistry.adapters.out.db.game;

import jakarta.persistence.Embeddable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageResourceJpaEmbeddable that)) return false;
        return Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUrl());
    }
}
