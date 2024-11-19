package be.kdg.int5.statistics.domain;

import java.util.Objects;

public record Achievement(AchievementId id, GameId gameId, int counterTotal, String title, String description) {
    public Achievement(AchievementId id, GameId gameId, int counterTotal, String title, String description) {
        this.id = Objects.requireNonNull(id);
        this.gameId = Objects.requireNonNull(gameId);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);

        if (counterTotal < 0) {
            throw new RuntimeException("Achievement total counter should be bigger than zero");
        }
        this.counterTotal = counterTotal;
    }
}
