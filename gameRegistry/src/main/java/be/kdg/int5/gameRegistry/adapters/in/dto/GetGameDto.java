package be.kdg.int5.gameRegistry.adapters.in.dto;

import java.util.UUID;

public class GetGameDto {
    private UUID gameId;

    public GetGameDto() {
    }

    public GetGameDto(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
