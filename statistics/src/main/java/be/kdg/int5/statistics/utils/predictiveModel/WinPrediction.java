package be.kdg.int5.statistics.utils.predictiveModel;

import java.util.UUID;

public class WinPrediction {
    private  UUID playerOneId;
    private float winProbabilityPlayerOne;
    private  UUID playerTwoId;
    private float winProbabilityPlayerTwo;

    public WinPrediction() {
    }

    public WinPrediction(UUID playerOneId, float winProbabilityPlayerOne, UUID playerTwoId, float winProbabilityPlayerTwo) {
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
