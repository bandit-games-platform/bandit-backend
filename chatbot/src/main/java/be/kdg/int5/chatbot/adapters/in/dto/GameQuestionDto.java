package be.kdg.int5.chatbot.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

public class GameQuestionDto {
    @NotNull
    private String gameId;
    @NotNull
    private String question;

    public GameQuestionDto() {
    }

    public GameQuestionDto(String gameId, String question) {
        this.gameId = gameId;
        this.question = question;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
