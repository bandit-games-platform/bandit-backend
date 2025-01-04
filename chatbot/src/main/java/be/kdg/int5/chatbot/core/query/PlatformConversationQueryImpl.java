package be.kdg.int5.chatbot.core.query;

import be.kdg.int5.chatbot.domain.PlatformConversation;
import be.kdg.int5.chatbot.domain.UserId;
import be.kdg.int5.chatbot.ports.in.query.PlatformConversationQuery;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlatformConversationQueryImpl implements PlatformConversationQuery {
    private final ConversationLoadPort conversationLoadPort;

    public PlatformConversationQueryImpl(ConversationLoadPort conversationLoadPort) {
        this.conversationLoadPort = conversationLoadPort;
    }

    @Override
    public PlatformConversation getLatestForPlayer(UUID playerId) {
        return conversationLoadPort.loadPlatformConversation(new UserId(playerId));
    }
}
