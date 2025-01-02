package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.PlatformConversation;
import be.kdg.int5.chatbot.domain.UserId;

public interface ConversationLoadPort {
    GameConversation loadGameConversation(UserId userId, GameId gameId);
    PlatformConversation loadPlatformConversation(UserId userId);
}
