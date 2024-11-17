package be.kdg.int5.chatbot.domain;

import java.util.Objects;

public class Question {
    private final String text;
    private Answer answer;

    public Question(String text, Answer answer) {
        Objects.requireNonNull(text);

        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
