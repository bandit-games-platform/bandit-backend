package be.kdg.int5.chatbot.adapters.in.dto;

public class FollowUpQuestionDto {
    private String userId;
    private String gameId;
    private QuestionDto question;

    public FollowUpQuestionDto() {
    }

    public FollowUpQuestionDto(String userId, String gameId, QuestionDto question) {
        this.userId = userId;
        this.gameId = gameId;
        this.question = question;
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

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }
}
