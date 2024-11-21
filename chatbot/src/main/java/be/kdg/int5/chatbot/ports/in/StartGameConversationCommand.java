package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.UserId;

import java.util.Objects;

public record StartGameConversationCommand(UserId userId, GameId gameId) {
    public StartGameConversationCommand {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(gameId);
    }
}
