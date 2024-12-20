package be.kdg.int5.statistics.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class WinProbabilityRequestDto {
    @NotNull
    private  UUID gameId;
    @NotNull
    private  UUID playerOneId;
    @NotNull
    private  UUID playerTwoId;

    public WinProbabilityRequestDto() {
    }

    public WinProbabilityRequestDto(UUID gameId, UUID playerOneId, UUID playerTwoId) {
        this.gameId = gameId;
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(UUID playerOneId) {
        this.playerOneId = playerOneId;
    }

    public UUID getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(UUID playerTwoId) {
        this.playerTwoId = playerTwoId;
    }
}
