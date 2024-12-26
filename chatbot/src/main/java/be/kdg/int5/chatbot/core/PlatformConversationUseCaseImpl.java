package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.PlatformConversation;
import be.kdg.int5.chatbot.ports.in.PlatformConversationCommand;
import be.kdg.int5.chatbot.ports.in.PlatformConversationUseCase;
import org.springframework.stereotype.Service;

@Service
public class PlatformConversationUseCaseImpl implements PlatformConversationUseCase {

    @Override
    public PlatformConversation updateConversation(PlatformConversationCommand command) {
        return null;
    }
}
