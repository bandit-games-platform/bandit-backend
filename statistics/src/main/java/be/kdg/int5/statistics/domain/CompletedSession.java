package be.kdg.int5.statistics.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class CompletedSession {
    private final SessionId sessionId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final GameEndState endState;

    private final Integer turnsTaken;
    private final Double avgSecondsPerTurn;
    private final Integer playerScore;
    private final Integer opponentScore;
    private final Integer clicks;
    private final String character;
    private final Boolean wasFirstToGo;

    public CompletedSession(final SessionId sessionId,
                            final LocalDateTime startTime,
                            final LocalDateTime endTime,
                            final GameEndState endState,
                            final Integer turnsTaken,
                            final Double avgSecondsPerTurn,
                            final Integer playerScore,
                            final Integer opponentScore,
                            final Integer clicks,
                            final String character,
                            final Boolean wasFirstToGo) {
        this.sessionId = Objects.requireNonNull(sessionId);
        this.startTime = Objects.requireNonNull(startTime);
        this.endTime = Objects.requireNonNull(endTime);
        this.endState = Objects.requireNonNull(endState);
        this.turnsTaken = turnsTaken;
        this.avgSecondsPerTurn = avgSecondsPerTurn;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.clicks = clicks;
        this.character = character;
        this.wasFirstToGo = wasFirstToGo;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public GameEndState getEndState() {
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
