package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.in.CreateOrUpdateGameProjectionCommand;
import be.kdg.int5.chatbot.ports.in.CreateOrUpdateGameProjectionUseCase;
import be.kdg.int5.chatbot.ports.out.AnswerAskPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsSavePort;
import be.kdg.int5.common.exceptions.PythonServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateOrUpdateGameProjectionUseCaseImpl implements CreateOrUpdateGameProjectionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateOrUpdateGameProjectionUseCaseImpl.class);
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final GameDetailsSavePort gameDetailsSavePort;
    private final AnswerAskPort answerAskPort;

    public CreateOrUpdateGameProjectionUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            GameDetailsSavePort gameDetailsSavePort,
            AnswerAskPort answerAskPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.gameDetailsSavePort = gameDetailsSavePort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional
    public void createOrUpdateGameProjection(CreateOrUpdateGameProjectionCommand command) {
        GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(new GameId(command.gameId()));
        if (gameDetails == null) {
            GameDetails newGameDetails = new GameDetails(
                    new GameId(command.gameId()),
                    command.title(),
                    command.description(),
                    command.rules());
            gameDetailsSavePort.saveNewGameDetails(newGameDetails);

            String gameSummary = askChatbotForGameSummary(newGameDetails);
            newGameDetails.setSummary(gameSummary);

            gameDetailsSavePort.updateGameDetails(newGameDetails);
            logger.info("chatbot: New projection created for game {}",
                    command.gameId());
        } else {
            gameDetails.setTitle(command.title());
            gameDetails.setDescription(command.description());
            gameDetails.setRules(command.rules());

            String gameSummary = askChatbotForGameSummary(gameDetails);
            gameDetails.setSummary(gameSummary);

            gameDetailsSavePort.updateGameDetails(gameDetails);
            logger.info("chatbot: Existing projection updated for game {}",
                    command.gameId());
        }
    }

    private String askChatbotForGameSummary(GameDetails gameDetails) {
        Question chatbotGameDescription = new Question(GameConversation.INITIAL_PROMPT, LocalDateTime.now(), true);

        try {
            Answer gameSummary = answerAskPort.getAnswerForInitialQuestion(gameDetails, chatbotGameDescription);
            chatbotGameDescription.update(gameSummary);
            return gameSummary.text();
        } catch(PythonServiceException e) {
            logger.info("Python service not available. GameSummary remain unset.");
        }
        return null;
    }
}
