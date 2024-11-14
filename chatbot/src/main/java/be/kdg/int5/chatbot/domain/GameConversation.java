package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

public class GameConversation extends Conversation {
    private final GameId gameId;

    public GameConversation(UserId userId, LocalDateTime lastMessageTime, LocalDateTime startTime, GameId gameId) {
        super(userId, lastMessageTime, startTime);
        this.gameId = gameId;
    }

    public GameConversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<Question> questions, GameId gameId) {
        super(userId, startTime, lastMessageTime, questions);
        this.gameId = gameId;
    }

    public GameId getGameId() {
        return gameId;
    }
}
