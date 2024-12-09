package be.kdg.int5.gameplay.domain;

import java.util.Objects;
import java.util.UUID;

public record DeveloperId(UUID uuid) {
    public DeveloperId {
        Objects.requireNonNull(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeveloperId that)) return false;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
