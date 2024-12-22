package be.kdg.int5.statistics.adapters.out.python.dto;

import java.util.UUID;

public class WinPredictionModeInputFeaturesDto {
    private UUID playerOneId;
    private UUID playerTwoId;
    private float diff_session_count;
    private float diff_avg_turns_taken;
    private float diff_avg_session_duration;
    private float diff_avg_seconds_per_turn;
    private float diff_avg_score;
    private float diff_avg_opponent_score;
    private float diff_win_rate;
    private float diff_avg_clicks;
    private float diff_avg_starting_first;

    public WinPredictionModeInputFeaturesDto( UUID playerOneId, UUID playerTwoId,float diff_session_count, float diff_win_rate, float diff_avg_score, float diff_avg_turns_taken, float diff_avg_session_duration, float diff_avg_seconds_per_turn, float diff_avg_clicks, float diff_avg_opponent_score, float diff_avg_starting_first) {
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
        this.diff_session_count = diff_session_count;
        this.diff_win_rate = diff_win_rate;
        this.diff_avg_score = diff_avg_score;
        this.diff_avg_turns_taken = diff_avg_turns_taken;
        this.diff_avg_session_duration = diff_avg_session_duration;
        this.diff_avg_seconds_per_turn = diff_avg_seconds_per_turn;
        this.diff_avg_clicks = diff_avg_clicks;
        this.diff_avg_opponent_score = diff_avg_opponent_score;
        this.diff_avg_starting_first = diff_avg_starting_first;
    }

    public UUID getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(UUID playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public UUID getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(UUID playerOneId) {
        this.playerOneId = playerOneId;
    }

    public float getDiff_win_rate() {
        return diff_win_rate;
    }

    public void setDiff_win_rate(float diff_win_rate) {
        this.diff_win_rate = diff_win_rate;
    }

    public float getDiff_avg_score() {
        return diff_avg_score;
    }

    public void setDiff_avg_score(float diff_avg_score) {
        this.diff_avg_score = diff_avg_score;
    }

    public float getDiff_avg_turns_taken() {
        return diff_avg_turns_taken;
    }

    public void setDiff_avg_turns_taken(float diff_avg_turns_taken) {
        this.diff_avg_turns_taken = diff_avg_turns_taken;
    }

    public float getDiff_avg_session_duration() {
        return diff_avg_session_duration;
    }

    public void setDiff_avg_session_duration(float diff_avg_session_duration) {
        this.diff_avg_session_duration = diff_avg_session_duration;
    }

    public float getDiff_avg_seconds_per_turn() {
        return diff_avg_seconds_per_turn;
    }

    public void setDiff_avg_seconds_per_turn(float diff_avg_seconds_per_turn) {
        this.diff_avg_seconds_per_turn = diff_avg_seconds_per_turn;
    }

    public float getDiff_avg_clicks() {
        return diff_avg_clicks;
    }

    public void setDiff_avg_clicks(float diff_avg_clicks) {
        this.diff_avg_clicks = diff_avg_clicks;
    }

    public float getDiff_avg_opponent_score() {
        return diff_avg_opponent_score;
    }

    public void setDiff_avg_opponent_score(float diff_avg_opponent_score) {
        this.diff_avg_opponent_score = diff_avg_opponent_score;
    }

    public float getDiff_avg_starting_first() {
        return diff_avg_starting_first;
    }

    public void setDiff_avg_starting_first(float diff_avg_starting_first) {
        this.diff_avg_starting_first = diff_avg_starting_first;
    }

    public float getDiff_session_count() {
        return diff_session_count;
    }

    public void setDiff_session_count(float diff_session_count) {
        this.diff_session_count = diff_session_count;
    }
}
