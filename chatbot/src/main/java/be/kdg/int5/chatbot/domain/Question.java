package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Question {
    private final String text;
    private final LocalDateTime submittedAt;
    private final boolean isInitial;
    private Answer answer;

    public Question(String text, LocalDateTime submittedAt, boolean isInitial) {
        this(text, submittedAt, isInitial, null);
    }

    public Question(String text, LocalDateTime submittedAt, boolean isInitial, Answer answer) {
        this.text = Objects.requireNonNull(text);
        this.submittedAt = Objects.requireNonNull(submittedAt);
        this.isInitial = isInitial;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public boolean isInitial() {
        return isInitial;
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

    @Override
    public String toString() {
        return """
                {"question": "%s", "answer": "%s"}
                """
        .formatted(
             text, answer.text()
        );
    }
}
