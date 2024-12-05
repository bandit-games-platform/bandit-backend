package be.kdg.int5.chatbot.adapters.out.python.dto;

import java.util.List;

public class PythonResponseDto {
    private String text;

    public PythonResponseDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
