package be.kdg.int5.chatbot.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

public class UpdatePlatformConversationDto {
    @NotNull
    private String questionText;

    public UpdatePlatformConversationDto() {
    }

    public UpdatePlatformConversationDto(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
