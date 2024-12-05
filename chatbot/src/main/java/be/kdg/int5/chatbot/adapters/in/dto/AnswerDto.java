package be.kdg.int5.chatbot.adapters.in.dto;

public class AnswerDto {
    private String text;

    public AnswerDto() {
    }

    public AnswerDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
