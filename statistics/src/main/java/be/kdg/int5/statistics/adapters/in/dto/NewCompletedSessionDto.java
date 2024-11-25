package be.kdg.int5.statistics.adapters.in.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class NewCompletedSessionDto {
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    @Pattern(regexp = "^(WIN|LOSS|DRAW)")
    private String endState;

    private Integer turnsTaken;
    private Double avgSecondsPerTurn;
    private Integer playerScore;
    private Integer opponentScore;
    private Integer clicks;
    private String character;
    private Boolean wasFirstToGo;

    public NewCompletedSessionDto() {
    }

    public NewCompletedSessionDto(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            String endState,
            Integer turnsTaken,
            Double avgSecondsPerTurn,
            Integer playerScore,
            Integer opponentScore,
            Integer clicks,
            String character,
            Boolean wasFirstToGo) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.endState = endState;
        this.turnsTaken = turnsTaken;
        this.avgSecondsPerTurn = avgSecondsPerTurn;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.clicks = clicks;
        this.character = character;
        this.wasFirstToGo = wasFirstToGo;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }

    public Integer getTurnsTaken() {
        return turnsTaken;
    }

    public void setTurnsTaken(Integer turnsTaken) {
        this.turnsTaken = turnsTaken;
    }

    public Double getAvgSecondsPerTurn() {
        return avgSecondsPerTurn;
    }

    public void setAvgSecondsPerTurn(Double avgSecondsPerTurn) {
        this.avgSecondsPerTurn = avgSecondsPerTurn;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(Integer playerScore) {
        this.playerScore = playerScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Boolean getWasFirstToGo() {
        return wasFirstToGo;
    }

    public void setWasFirstToGo(Boolean wasFirstToGo) {
        this.wasFirstToGo = wasFirstToGo;
    }
}
