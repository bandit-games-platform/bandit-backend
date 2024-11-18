package be.kdg.int5.statistics.domain;

import java.util.Objects;

public class Achievement {
    private final AchievementId id;
    private final GameId gameId;
    private final int counterTotal;

    public Achievement(AchievementId id, GameId gameId, int counterTotal) {
        this.id = Objects.requireNonNull(id);
        this.gameId = Objects.requireNonNull(gameId);

        if (counterTotal < 0){
            throw new RuntimeException("Achievement total counter should be bigger than zero");
        }
        this.counterTotal = counterTotal;
    }


    public AchievementId getId() {
        return id;
    }

    public GameId getGameId() {
        return gameId;
    }

    public int getCounterTotal() {
        return counterTotal;
    }
}
