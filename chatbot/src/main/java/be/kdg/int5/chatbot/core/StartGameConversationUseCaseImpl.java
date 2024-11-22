package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.StartGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import be.kdg.int5.chatbot.ports.out.ConversationStartPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StartGameConversationUseCaseImpl implements StartGameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final ConversationStartPort conversationStartPort;

    public StartGameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            ConversationSavePort conversationSavePort,
            ConversationStartPort conversationStartPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.conversationStartPort = conversationStartPort;
    }

    @Override
    @Transactional
    public Answer startGameConversation(StartGameConversationCommand command) {
        // load GameDetails
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        // create conversation
        final LocalDateTime now = LocalDateTime.now();
        final GameConversation gameConversation = new GameConversation(
                command.userId(),
                now,
                now,
                command.gameId()
        );

        // start conversation
        final Question initialQuestion = new Question(GameConversation.initialPrompt);
        final Answer answer = conversationStartPort.startGameConversation(gameDetails, gameConversation, initialQuestion);

        // add question-answer pair to the conversation
        initialQuestion.setAnswer(answer);
        gameConversation.addQuestion(initialQuestion);

        // save conservation
        conversationSavePort.saveConversation(gameConversation);

        // return conversation
        return answer;
    }
}
