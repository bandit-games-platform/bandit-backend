package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.ports.in.StartConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StartConversationUseCaseImpl implements StartConversationUseCase {
    private final ConversationSavePort conversationSavePort;

    public StartConversationUseCaseImpl(ConversationSavePort conversationSavePort) {
        this.conversationSavePort = conversationSavePort;
    }

    @Override
    @Transactional
    public GameConversation startGameConversation(StartGameConversationCommand command) {
        final LocalDateTime now = LocalDateTime.now();

        // create conversation
        final GameConversation gameConversation = new GameConversation(
                command.userId(),
                now,
                now,
                command.gameId()
        );

        // save conversation
        conversationSavePort.saveConversation(gameConversation);

        // return conversation
        return gameConversation;
    }
}
