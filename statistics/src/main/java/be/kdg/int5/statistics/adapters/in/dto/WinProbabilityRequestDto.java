package be.kdg.int5.statistics.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class WinProbabilityRequestDto {
    @NotNull
    private  UUID playerOneId;
    @NotNull
    private  UUID playerTwoId;

    public WinProbabilityRequestDto() {
    }

    public WinProbabilityRequestDto(UUID playerOneId, UUID playerTwoId) {
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
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
