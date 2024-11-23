package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Question {
    private final String text;
    private final LocalDateTime submittedAt;
    private Answer answer;

    public Question(String text, LocalDateTime submittedAt) {
        this.text = Objects.requireNonNull(text);
        this.submittedAt = Objects.requireNonNull(submittedAt);
    }

    public Question(String text, LocalDateTime submittedAt, Answer answer) {
        this(text, submittedAt);
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void update(Answer answer) {
        setAnswer(answer);
    }
}
