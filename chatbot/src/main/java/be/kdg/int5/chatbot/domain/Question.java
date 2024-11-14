package be.kdg.int5.chatbot.domain;

public class Question {
    private String text;
    private Answer answer;

    public Question(String text, Answer answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
