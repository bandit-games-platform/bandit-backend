package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.Conversation;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.UserId;

public interface ConversationLoadPort {
    Conversation loadGameConversation(UserId userId, GameId gameId);
}
