package be.kdg.int5.common.domain;

import java.util.Objects;

public record ResourceURL(String url) {
    public ResourceURL {
        Objects.requireNonNull(url);
    }
}
