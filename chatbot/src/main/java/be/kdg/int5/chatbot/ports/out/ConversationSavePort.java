package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.Conversation;

public interface ConversationSavePort {
    void saveConversation(Conversation conversation);
}
