package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.ports.in.StartConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import org.springframework.stereotype.Service;

@Service
public class StartConversationUseCaseImpl implements StartConversationUseCase {
    private final ConversationLoadPort conversationLoadPort;

    public StartConversationUseCaseImpl(ConversationLoadPort conversationLoadPort) {
        this.conversationLoadPort = conversationLoadPort;
    }

    @Override
    public void startGameConversation(StartGameConversationCommand command) {

    }
}
