package be.kdg.int5.gameplay.domain;

import java.util.Objects;

public record GameDeveloperProjection(GameId gameId, DeveloperId developerId, String gameTitle) {
    public GameDeveloperProjection {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(developerId);
    }
}
