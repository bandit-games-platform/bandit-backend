package be.kdg.int5.gameRegistry.domain;

import java.util.Objects;
import java.util.UUID;

public record AchievementId(UUID uuid) {

    public AchievementId {
        Objects.requireNonNull(uuid);
    }
}
