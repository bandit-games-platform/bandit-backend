package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.domain.UserId;

import java.util.Objects;

public record FollowUpGameConversationCommand(UserId userId, GameId gameId, String question) {
    public FollowUpGameConversationCommand {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(question);
    }
}
