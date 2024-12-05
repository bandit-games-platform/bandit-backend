package be.kdg.int5.chatbot.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

public class QuestionDto {
    @NotNull
    private String text;

    public QuestionDto() {
    }

    public QuestionDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
