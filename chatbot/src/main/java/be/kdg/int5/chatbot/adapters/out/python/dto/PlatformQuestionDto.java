package be.kdg.int5.chatbot.adapters.out.python.dto;

import java.util.List;

public class PlatformQuestionDto {
    private String question;
    private String currentPage;
    private List<QuestionAnswerDto> previousQuestionsList;

    public PlatformQuestionDto() {
    }

    public PlatformQuestionDto(String question, String currentPage, List<QuestionAnswerDto> previousQuestionsList) {
        this.question = question;
        this.currentPage = currentPage;
        this.previousQuestionsList = previousQuestionsList;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<QuestionAnswerDto> getPreviousQuestionsList() {
        return previousQuestionsList;
    }

    public void setPreviousQuestionsList(List<QuestionAnswerDto> previousQuestionsList) {
        this.previousQuestionsList = previousQuestionsList;
    }
}
