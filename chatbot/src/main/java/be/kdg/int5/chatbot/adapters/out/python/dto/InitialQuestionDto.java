package be.kdg.int5.chatbot.adapters.out.python.dto;

public class InitialQuestionDto {
    private String initialPrompt;
    private GameDetailsDto gameDetailsDto;

    public InitialQuestionDto() {
    }

    public InitialQuestionDto(String initialPrompt, GameDetailsDto gameDetailsDto) {
        this.initialPrompt = initialPrompt;
        this.gameDetailsDto = gameDetailsDto;
    }

    public String getInitialPrompt() {
        return initialPrompt;
    }

    public void setInitialPrompt(String initialPrompt) {
        this.initialPrompt = initialPrompt;
    }

    public GameDetailsDto getGameDetailsDto() {
        return gameDetailsDto;
    }

    public void setGameDetailsDto(GameDetailsDto gameDetailsDto) {
        this.gameDetailsDto = gameDetailsDto;
    }
}
