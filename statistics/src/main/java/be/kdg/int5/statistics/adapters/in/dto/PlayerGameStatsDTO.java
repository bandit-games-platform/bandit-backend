package be.kdg.int5.statistics.adapters.in.dto;

import org.springframework.lang.NonNull;

import java.util.*;

public class PlayerGameStatsDTO {

    @NonNull
    private final UUID playerId;

    @NonNull
    private final UUID gameId;

    public PlayerGameStatsDTO(UUID playerId, UUID gameId) {
        this.playerId = Objects.requireNonNull(playerId);
        this.gameId = Objects.requireNonNull(gameId);
    }

    @NonNull
    public UUID getPlayerId() {
        return playerId;
    }

    @NonNull
    public UUID getGameId() {
        return gameId;
    }
}
