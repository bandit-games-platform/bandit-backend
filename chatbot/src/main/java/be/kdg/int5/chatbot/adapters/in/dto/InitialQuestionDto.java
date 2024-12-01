package be.kdg.int5.chatbot.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

public class InitialQuestionDto {
    @NotNull
    private String gameId;

    public InitialQuestionDto() {
    }

    public InitialQuestionDto(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
