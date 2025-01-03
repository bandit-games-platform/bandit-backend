package be.kdg.int5.chatbot.adapters.in.dto;

import be.kdg.int5.chatbot.domain.Answer;

import java.time.LocalDateTime;

public class LoadQuestionDto {
    private String text;
    private LocalDateTime submittedAt;
    private AnswerDto answer;

    public LoadQuestionDto() {
    }

    public LoadQuestionDto(String text, LocalDateTime submittedAt, AnswerDto answer) {
        this.text = text;
        this.submittedAt = submittedAt;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public AnswerDto getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDto answer) {
        this.answer = answer;
    }
}
