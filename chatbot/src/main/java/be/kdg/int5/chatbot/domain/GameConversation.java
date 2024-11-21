package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

public class GameConversation extends Conversation {
    public static final String initialPrompt = " Provide a very short paragraph to describe the game. Additionally, can you summarize the rules for this game in as few words as possible?";
    private final GameId gameId;

    public GameConversation(UserId userId, LocalDateTime lastMessageTime, LocalDateTime startTime, GameId gameId) {
        this(userId, startTime, lastMessageTime, null, gameId);
    }

    public GameConversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<Question> questions, GameId gameId) {
        super(userId, startTime, lastMessageTime, questions);
        this.gameId = gameId;
    }

    public GameId getGameId() {
        return gameId;
    }
}
