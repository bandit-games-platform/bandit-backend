package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.ports.in.CreateOrUpdateGameProjectionCommand;
import be.kdg.int5.chatbot.ports.in.CreateOrUpdateGameProjectionUseCase;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsSavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrUpdateGameProjectionUseCaseImpl implements CreateOrUpdateGameProjectionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateOrUpdateGameProjectionUseCaseImpl.class);
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final GameDetailsSavePort gameDetailsSavePort;

    public CreateOrUpdateGameProjectionUseCaseImpl(GameDetailsLoadPort gameDetailsLoadPort, GameDetailsSavePort gameDetailsSavePort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.gameDetailsSavePort = gameDetailsSavePort;
    }

    @Override
    @Transactional
    public void createOrUpdateGameProjection(CreateOrUpdateGameProjectionCommand command) {
        GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(new GameId(command.gameId()));
        if (gameDetails == null) {
            gameDetailsSavePort.saveNewGameDetails(new GameDetails(
                    new GameId(command.gameId()),
                    command.title(),
                    command.description(),
                    command.rules()
            ));
            logger.info("chatbot: New projection created for game {}",
                    command.gameId());
        } else {
            gameDetails.setTitle(command.title());
            gameDetails.setDescription(command.description());
            gameDetails.setRules(command.rules());
            logger.info("chatbot: Existing projection updated for game {}",
                    command.gameId());
            gameDetailsSavePort.updateGameDetails(gameDetails);
        }
    }
}
