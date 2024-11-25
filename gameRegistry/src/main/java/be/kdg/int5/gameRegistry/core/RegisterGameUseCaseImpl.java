package be.kdg.int5.gameRegistry.core;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.domain.Developer;
import be.kdg.int5.gameRegistry.domain.DeveloperId;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.domain.GameId;
import be.kdg.int5.gameRegistry.port.in.RegisterGameCommand;
import be.kdg.int5.gameRegistry.port.in.RegisterGameUseCase;
import be.kdg.int5.gameRegistry.port.out.DeveloperLoadPort;
import be.kdg.int5.gameRegistry.port.out.GamesCreatePort;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import be.kdg.int5.gameRegistry.port.out.GamesUpdatePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class RegisterGameUseCaseImpl implements RegisterGameUseCase {
    private final Logger logger = LoggerFactory.getLogger(RegisterGameUseCaseImpl.class);

    private final GamesLoadPort gamesLoadPort;
    private final List<GamesCreatePort> gamesCreatePort;
    private final DeveloperLoadPort developerLoadPort;
    private final List<GamesUpdatePort> gamesUpdatePort;

    public RegisterGameUseCaseImpl(GamesLoadPort gamesLoadPort, List<GamesCreatePort> gamesCreatePort, DeveloperLoadPort developerLoadPort, List<GamesUpdatePort> gamesUpdatePort) {
        this.gamesLoadPort = gamesLoadPort;
        this.gamesCreatePort = gamesCreatePort;
        this.developerLoadPort = developerLoadPort;
        this.gamesUpdatePort = gamesUpdatePort;
    }

    @Override
    @Transactional
    public GameId registerGame(RegisterGameCommand command) {
        GameId gameId = generateUniqueIdFromDeveloperAndTitle(command.developerId(), command.title());
        logger.info("gameRegistry:register-game Generated gameId is '{}' for title '{}' and developerId '{}'",
                gameId,
                command.title(),
                command.developerId()
        );

        Game existingGame = gamesLoadPort.loadGameByIdWithDetails(gameId.uuid());

        if (existingGame == null) {
            //Creation mode
            Developer gameDev = developerLoadPort.load(command.developerId());
            if (gameDev == null) gameDev = new Developer(command.developerId(), command.developerId().toString());
            logger.info("gameRegistry:register-game [CREATION MODE] developer name is '{}'", gameDev.studioName());

            if (command.icon() == null || command.background() == null) throw new NullPointerException();

            Game newGame = new Game(
                    gameId,
                    command.title(),
                    command.description(),
                    command.currentPrice(),
                    new ImageResource(new ResourceURL(command.icon())),
                    new ImageResource(new ResourceURL(command.background())),
                    command.rules(),
                    command.currentHost(),
                    gameDev,
                    command.screenshots(),
                    command.achievements()
            );

            boolean gameCreated = false;
            for (GamesCreatePort gamesCreate: gamesCreatePort) {
                gameCreated |= gamesCreate.create(newGame);
            }
            if(gameCreated) return gameId;
        }else {
            //Patch mode
            logger.info("gameRegistry:register-game [PATCH MODE] for existing game: '{}'", existingGame);

            if (command.currentHost() != null) existingGame.setCurrentHost(command.currentHost());
            if (command.description() != null) existingGame.setDescription(command.description());
            if (command.currentPrice() != null) existingGame.setCurrentPrice(command.currentPrice());
            if (command.icon() != null) existingGame.setIcon(new ImageResource(new ResourceURL(command.icon())));
            if (command.background() != null) existingGame.setBackground(new ImageResource(new ResourceURL(command.background())));
            if (command.screenshots() != null) existingGame.setScreenshots(command.screenshots());
            if (command.achievements() != null) existingGame.setAchievements(command.achievements());
            if (command.rules() != null) existingGame.setRules(command.rules());

            boolean gameUpdated = false;
            for (GamesUpdatePort gamesUpdate: gamesUpdatePort) {
                gameUpdated |= gamesUpdate.update(existingGame);
            }
            if(gameUpdated) return gameId;
        }
        return null;
    }

    private GameId generateUniqueIdFromDeveloperAndTitle(DeveloperId developerId, String title) {
        return new GameId(UUID.nameUUIDFromBytes((developerId.toString()+":"+title).getBytes(StandardCharsets.UTF_8)));
    }
}
