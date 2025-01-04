package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.StartGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import be.kdg.int5.chatbot.ports.out.AnswerAskPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import be.kdg.int5.common.exceptions.ConversationWithoutInitialQuestionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StartGameConversationUseCaseImpl implements StartGameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final ConversationLoadPort conversationLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final AnswerAskPort answerAskPort;

    public StartGameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            ConversationLoadPort conversationLoadPort,
            ConversationSavePort conversationSavePort,
            AnswerAskPort answerAskPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.conversationLoadPort = conversationLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional
    public GameConversation startGameConversation(StartGameConversationCommand command) {
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        GameConversation gameConversation = conversationLoadPort.loadGameConversation(command.userId(), command.gameId());

        if (gameConversation == null) {
            gameConversation = new GameConversation(command.userId(), command.gameId());
            Question initialQuestion = gameConversation.start();

            // pre-load answer
//            Answer answer = gameDetails.getSummary();

            // update question & conversation
//            initialQuestion.update(answer); // answer
            gameConversation.update(initialQuestion);

            // save conversation
            conversationSavePort.saveConversation(gameConversation);
            return gameConversation;
        }



//        if (existingGameConversation != null) {
//            Question initialQuestion = existingGameConversation.getInitialQuestion();
//
//            if (initialQuestion == null) {
//                throw new ConversationWithoutInitialQuestionException("Conversation exists but has no initial question.");
//            }
//            return initialQuestion.getAnswer();
//        }

//        final GameConversation gameConversation = new GameConversation(command.userId(), command.gameId());
//
//        final Question followUpQuestion = gameConversation.start();
//        existingGameConversation.update(followUpQuestion);
//
//        final Answer answer = answerAskPort.getAnswerForInitialQuestion(gameDetails, followUpQuestion);
//
//        followUpQuestion.update(answer);
//        gameConversation.update(followUpQuestion);
//
//        conversationSavePort.saveConversation(gameConversation);
//
//        return answer;
    }
}
