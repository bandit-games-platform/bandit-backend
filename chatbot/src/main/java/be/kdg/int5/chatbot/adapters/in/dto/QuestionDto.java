package be.kdg.int5.chatbot.adapters.in.dto;

public class QuestionDto {
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
