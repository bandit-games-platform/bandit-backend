package be.kdg.int5.common.domain;

import java.util.Objects;

public record ImageResource(ResourceURL url) {
    public ImageResource {
        Objects.requireNonNull(url);
    }
}
