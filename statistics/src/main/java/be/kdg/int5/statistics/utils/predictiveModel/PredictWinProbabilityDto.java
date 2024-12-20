package be.kdg.int5.statistics.utils.predictiveModel;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PredictWinProbabilityDto {
    @NotNull
    private UUID playerId;
    @NotNull
    private float avg_turns_taken;
    @NotNull
    private float avg_session_duration;
    @NotNull
    private float avg_score;
    @NotNull
    private float avg_opponent_score;
    @NotNull
    private float avg_seconds_per_turn;
    @NotNull
    private float win_rate;
    @NotNull
    private float avg_starting_first;
    @NotNull
    private float clicks;


    public PredictWinProbabilityDto() {
    }

    public PredictWinProbabilityDto(UUID playerId, float avg_turns_taken, float avg_session_duration, float avg_score, float avg_opponent_score, float avg_seconds_per_turn, float win_rate, float avg_starting_first, float clicks) {
        this.playerId = playerId;
        this.avg_turns_taken = avg_turns_taken;
        this.avg_session_duration = avg_session_duration;
        this.avg_score = avg_score;
        this.avg_opponent_score = avg_opponent_score;
        this.avg_seconds_per_turn = avg_seconds_per_turn;
        this.win_rate = win_rate;
        this.avg_starting_first = avg_starting_first;
        this.clicks = clicks;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public float getAvg_turns_taken() {
        return avg_turns_taken;
    }

    public void setAvg_turns_taken(float avg_turns_taken) {
        this.avg_turns_taken = avg_turns_taken;
    }

    public float getAvg_session_duration() {
        return avg_session_duration;
    }

    public void setAvg_session_duration(float avg_session_duration) {
        this.avg_session_duration = avg_session_duration;
    }

    public float getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(float avg_score) {
        this.avg_score = avg_score;
    }

    public float getAvg_opponent_score() {
        return avg_opponent_score;
    }

    public void setAvg_opponent_score(float avg_opponent_score) {
        this.avg_opponent_score = avg_opponent_score;
    }

    public float getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(float win_rate) {
        this.win_rate = win_rate;
    }

    public float getAvg_starting_first() {
        return avg_starting_first;
    }

    public void setAvg_starting_first(float avg_starting_first) {
        this.avg_starting_first = avg_starting_first;
    }

    public float getAvg_seconds_per_turn() {
        return avg_seconds_per_turn;
    }

    public void setAvg_seconds_per_turn(float avgSecondsPerTurn) {
        this.avg_seconds_per_turn = avgSecondsPerTurn;
    }

    public float getClicks() {
        return clicks;
    }

    public void setClicks(float clicks) {
        this.clicks = clicks;
    }
}
