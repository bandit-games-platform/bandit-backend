package be.kdg.int5.chatbot.adapters.in.dto;

public class InitialQuestionDto {
    private String userId;
    private String gameId;

    public InitialQuestionDto() {
    }

    public InitialQuestionDto(String userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
