package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.StartGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import be.kdg.int5.chatbot.ports.out.AnswerAskPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StartGameConversationUseCaseImpl implements StartGameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final AnswerAskPort answerAskPort;

    public StartGameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            ConversationSavePort conversationSavePort,
            AnswerAskPort answerAskPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional
    public Answer startGameConversation(StartGameConversationCommand command) {
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        final GameConversation gameConversation = new GameConversation(command.userId(), command.gameId());

        final Question initialQuestion = gameConversation.start();
        final Answer answer = answerAskPort.getAnswerForInitialQuestion(gameDetails, initialQuestion);

        initialQuestion.update(answer);
        gameConversation.update(initialQuestion);

        conversationSavePort.saveConversation(gameConversation);

        return answer;
    }
}
