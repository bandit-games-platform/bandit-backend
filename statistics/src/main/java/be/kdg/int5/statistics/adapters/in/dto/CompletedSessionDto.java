package be.kdg.int5.statistics.adapters.in.dto;

import java.time.LocalDateTime;
import java.util.*;

public class CompletedSessionDto {
    private final UUID sessionId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String endState;
    private final Integer turnsTaken;
    private final Double avgSecondsPerTurn;
    private final Integer playerScore;
    private final Integer opponentScore;
    private final Integer clicks;
    private final String character;
    private final Boolean wasFirstToGo;

    public CompletedSessionDto(
            UUID sessionId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String endState,
            Integer turnsTaken,
            Double avgSecondsPerTurn,
            Integer playerScore,
            Integer opponentScore,
            Integer clicks,
            String character,
            Boolean wasFirstToGo
    ) {
        this.sessionId = Objects.requireNonNull(sessionId);
        this.startTime = Objects.requireNonNull(startTime);
        this.endTime = Objects.requireNonNull(endTime);
        this.endState = endState;
        this.turnsTaken = turnsTaken;
        this.avgSecondsPerTurn = avgSecondsPerTurn;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.clicks = clicks;
        this.character = character;
        this.wasFirstToGo = wasFirstToGo;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getEndState() {
        return endState;
    }

    public Integer getTurnsTaken() {
        return turnsTaken;
    }

    public Double getAvgSecondsPerTurn() {
        return avgSecondsPerTurn;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public Integer getClicks() {
        return clicks;
    }

    public String getCharacter() {
        return character;
    }

    public Boolean getWasFirstToGo() {
        return wasFirstToGo;
    }
}

