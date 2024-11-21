package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.domain.Conversation;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.UserId;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import org.springframework.stereotype.Repository;

@Repository
public class ConversationJpaAdapter implements ConversationLoadPort {

    @Override
    public Conversation loadGameConversation(UserId userId, GameId gameId) {
        return null;
    }
}
