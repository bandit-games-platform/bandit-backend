package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.GameConversation;

public interface StartConversationUseCase {
    GameConversation startGameConversation(StartGameConversationCommand command);
}
