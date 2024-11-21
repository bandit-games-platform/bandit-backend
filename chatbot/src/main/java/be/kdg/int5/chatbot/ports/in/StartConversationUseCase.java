package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.Answer;

public interface StartConversationUseCase {
    Answer startGameConversation(StartGameConversationCommand command);
}
