package be.kdg.int5.chatbot.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

public class FollowUpQuestionDto {
    @NotNull
    private String gameId;
    private QuestionDto question;

    public FollowUpQuestionDto() {
    }

    public FollowUpQuestionDto(String gameId, QuestionDto question) {
        this.gameId = gameId;
        this.question = question;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }
}
