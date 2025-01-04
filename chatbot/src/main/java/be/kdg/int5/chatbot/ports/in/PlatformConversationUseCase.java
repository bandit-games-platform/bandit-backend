package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.PlatformConversation;

public interface PlatformConversationUseCase {
    PlatformConversation updateConversation(PlatformConversationCommand command);
}
