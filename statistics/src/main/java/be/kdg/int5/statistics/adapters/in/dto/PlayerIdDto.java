package be.kdg.int5.statistics.adapters.in.dto;

import java.util.UUID;

public class PlayerIdDto {
    private  UUID playerId;

    public PlayerIdDto() {
    }

    public PlayerIdDto(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}

