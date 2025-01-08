package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.UserId;

import java.util.Objects;

public record GameConversationCommand(UserId userId, GameId gameId, String question) {
    public GameConversationCommand {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(gameId);
    }
}
