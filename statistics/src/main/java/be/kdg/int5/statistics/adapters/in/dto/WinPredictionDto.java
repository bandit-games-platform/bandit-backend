package be.kdg.int5.statistics.adapters.in.dto;

import java.util.UUID;

public class WinPredictionDto {
    private  UUID playerOneId;
    private float probabilityPlayerOne;
    private  UUID playerTwoId;
    private float probabilityPlayerTwo;

    public WinPredictionDto() {
    }

    public WinPredictionDto(UUID playerOneId, float probabilityPlayerOne, UUID playerTwoId, float probabilityPlayerTwo) {
        this.playerOneId = playerOneId;
        this.probabilityPlayerOne = probabilityPlayerOne;
        this.playerTwoId = playerTwoId;
        this.probabilityPlayerTwo = probabilityPlayerTwo;
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(UUID playerOneId) {
        this.playerOneId = playerOneId;
    }

    public float getProbabilityPlayerOne() {
        return probabilityPlayerOne;
    }

    public void setProbabilityPlayerOne(float probabilityPlayerOne) {
        this.probabilityPlayerOne = probabilityPlayerOne;
    }

    public UUID getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(UUID playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public float getProbabilityPlayerTwo() {
        return probabilityPlayerTwo;
    }

    public void setProbabilityPlayerTwo(float probabilityPlayerTwo) {
        this.probabilityPlayerTwo = probabilityPlayerTwo;
    }
}
