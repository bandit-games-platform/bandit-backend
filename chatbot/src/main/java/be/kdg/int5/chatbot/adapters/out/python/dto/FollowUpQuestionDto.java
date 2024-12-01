package be.kdg.int5.chatbot.adapters.out.python.dto;

import java.util.List;

public class FollowUpQuestionDto {
    private String followUpPrompt;
    private GameDetailsDto gameDetailsDto;
    private List<QuestionAnswerDto> previousQuestionsList;

    public FollowUpQuestionDto() {
    }

    public FollowUpQuestionDto(String followUpPrompt, GameDetailsDto gameDetailsDto, List<QuestionAnswerDto> previousQuestionsList) {
        this.followUpPrompt = followUpPrompt;
        this.gameDetailsDto = gameDetailsDto;
        this.previousQuestionsList = previousQuestionsList;
    }

    public String getFollowUpPrompt() {
        return followUpPrompt;
    }

    public void setFollowUpPrompt(String followUpPrompt) {
        this.followUpPrompt = followUpPrompt;
    }

    public GameDetailsDto getGameDetailsDto() {
        return gameDetailsDto;
    }

    public void setGameDetailsDto(GameDetailsDto gameDetailsDto) {
        this.gameDetailsDto = gameDetailsDto;
    }

    public List<QuestionAnswerDto> getPreviousQuestionsList() {
        return previousQuestionsList;
    }

    public void setPreviousQuestionsList(List<QuestionAnswerDto> previousQuestionsList) {
        this.previousQuestionsList = previousQuestionsList;
    }
}
