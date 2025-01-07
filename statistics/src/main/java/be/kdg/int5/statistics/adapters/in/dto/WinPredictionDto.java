package be.kdg.int5.statistics.adapters.in.dto;

import java.util.UUID;

public class WinPredictionDto {
    private  UUID playerOneId;
    private float winProbabilityPlayerOne;
    private  UUID playerTwoId;
    private float winProbabilityPlayerTwo;

    public WinPredictionDto() {
    }

    public WinPredictionDto(UUID playerOneId, float winProbabilityPlayerOne, UUID playerTwoId, float winProbabilityPlayerTwo) {
        this.playerOneId = playerOneId;
        this.winProbabilityPlayerOne = winProbabilityPlayerOne;
        this.playerTwoId = playerTwoId;
        this.winProbabilityPlayerTwo = winProbabilityPlayerTwo;
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(UUID playerOneId) {
        this.playerOneId = playerOneId;
    }

    public float getWinProbabilityPlayerOne() {
        return winProbabilityPlayerOne;
    }

    public void setWinProbabilityPlayerOne(float winProbabilityPlayerOne) {
        this.winProbabilityPlayerOne = winProbabilityPlayerOne;
    }

    public UUID getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(UUID playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public float getWinProbabilityPlayerTwo() {
        return winProbabilityPlayerTwo;
    }

    public void setWinProbabilityPlayerTwo(float winProbabilityPlayerTwo) {
        this.winProbabilityPlayerTwo = winProbabilityPlayerTwo;
    }
}
