package be.kdg.int5.chatbot.ports.in.query;

import be.kdg.int5.chatbot.domain.PlatformConversation;

import java.util.UUID;

public interface PlatformConversationQuery {
    PlatformConversation getLatestForPlayer(UUID playerId);
}
