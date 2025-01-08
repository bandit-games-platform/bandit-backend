package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.GameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.GameConversationCommand;
import be.kdg.int5.chatbot.ports.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class GameConversationUseCaseImpl implements GameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final GameDetailsSavePort gameDetailsSavePort;
    private final ConversationLoadPort conversationLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final AnswerAskPort answerAskPort;

    public GameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            GameDetailsSavePort gameDetailsSavePort,
            ConversationLoadPort conversationLoadPort,
            ConversationSavePort conversationSavePort,
            AnswerAskPort answerAskPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.gameDetailsSavePort = gameDetailsSavePort;
        this.conversationLoadPort = conversationLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional
    public GameConversation updateGameConversation(GameConversationCommand command) {
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        GameConversation gameConversation = conversationLoadPort.loadGameConversation(command.userId(), command.gameId());

        if (gameConversation == null) {
            gameConversation = new GameConversation(command.userId(), command.gameId());
            Question initialQuestion = gameConversation.start();

            // pre-load answer
            Answer initialAnswer;

            if (!gameDetails.hasSummary()) {
                initialAnswer = askChatbotForGameSummary(gameDetails);
                gameDetails.setSummary(initialAnswer.text());
                gameDetailsSavePort.updateGameDetails(gameDetails);
            } else {
                initialAnswer = new Answer(gameDetails.getSummary());
            }

            // update question & conversation
            initialQuestion.update(initialAnswer); // answer
            gameConversation.update(initialQuestion);

            // save conversation
            conversationSavePort.saveConversation(gameConversation);
            return gameConversation;
        }

        Question newQuestion = gameConversation.addFollowUpQuestion(command.question());
        final List<Question> previousQuestionWindowList = gameConversation.getPreviousQuestionsInWindow();

        gameConversation.update(newQuestion);
        conversationSavePort.saveConversation(gameConversation);

        final Answer answer = answerAskPort.getAnswerForFollowUpGameQuestion(gameDetails, previousQuestionWindowList, newQuestion);
        System.out.println(answer.text());

        newQuestion.update(answer);
        gameConversation.update(newQuestion);
        conversationSavePort.saveConversation(gameConversation);

        return gameConversation;
    }

    private Answer askChatbotForGameSummary(GameDetails gameDetails) {
        Question chatbotGameDescription = new Question(GameConversation.INITIAL_PROMPT, LocalDateTime.now(), true);
        return answerAskPort.getAnswerForInitialGameQuestion(gameDetails, chatbotGameDescription);
    }
}
