package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.domain.UserId;

import java.util.Objects;

public record ContinueGameConversationCommand(GameConversation gameConversation, Question question) {
    public ContinueGameConversationCommand {
        Objects.requireNonNull(gameConversation);
        Objects.requireNonNull(question);
    }
}
