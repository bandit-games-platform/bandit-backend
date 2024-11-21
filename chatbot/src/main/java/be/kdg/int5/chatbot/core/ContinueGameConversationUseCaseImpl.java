package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.ports.in.ContinueConversationUseCase;
import be.kdg.int5.chatbot.ports.in.ContinueGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ContinueGameConversationUseCaseImpl implements ContinueConversationUseCase {
    private final ConversationLoadPort conversationLoadPort;
    private final ConversationSavePort conversationSavePort;

    public ContinueGameConversationUseCaseImpl(ConversationLoadPort conversationLoadPort, ConversationSavePort conversationSavePort) {
        this.conversationLoadPort = conversationLoadPort;
        this.conversationSavePort = conversationSavePort;
    }

    @Override
    @Transactional
    public void continueGameConversation(ContinueGameConversationCommand command) {
        final LocalDateTime now = LocalDateTime.now();

        final GameConversation gameConversation = command.gameConversation();
        gameConversation.addQuestion(command.question());

        conversationSavePort.saveConversation(gameConversation);
    }
}
