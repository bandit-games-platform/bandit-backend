package be.kdg.int5.gameRegistry.core;

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
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class RegisterGameUseCaseImpl implements RegisterGameUseCase {
    private final GamesLoadPort gamesLoadPort;
    private final GamesCreatePort gamesCreatePort;
    private final DeveloperLoadPort developerLoadPort;
    private final GamesUpdatePort gamesUpdatePort;

    public RegisterGameUseCaseImpl(GamesLoadPort gamesLoadPort, GamesCreatePort gamesCreatePort, DeveloperLoadPort developerLoadPort, GamesUpdatePort gamesUpdatePort) {
        this.gamesLoadPort = gamesLoadPort;
        this.gamesCreatePort = gamesCreatePort;
        this.developerLoadPort = developerLoadPort;
        this.gamesUpdatePort = gamesUpdatePort;
    }

    @Override
    public GameId registerGame(RegisterGameCommand command) {
        GameId gameId = generateUniqueIdFromDeveloperAndTitle(command.developerId(), command.title());

        Game existingGame = gamesLoadPort.loadGameByIdWithDetails(gameId.uuid());

        if (existingGame == null) {
            //Creation mode
            Developer gameDev = developerLoadPort.load(command.developerId());
            if (gameDev == null) gameDev = new Developer(command.developerId(), command.developerId().toString());

            Game newGame = new Game(
                    gameId,
                    command.title(),
                    command.description(),
                    command.currentPrice(),
                    command.icon(),
                    command.background(),
                    command.rules(),
                    command.currentHost(),
                    gameDev,
                    command.screenshots(),
                    command.achievements()
            );

            if(gamesCreatePort.create(newGame)) return gameId;
        }else {
            //Patch mode
            if (command.currentHost() != null) existingGame.setCurrentHost(command.currentHost());
            if (command.description() != null) existingGame.setDescription(command.description());
            if (command.currentPrice() != null) existingGame.setCurrentPrice(command.currentPrice());
            if (command.icon() != null) existingGame.setIcon(command.icon());
            if (command.background() != null) existingGame.setBackground(command.background());
            if (command.screenshots() != null) existingGame.setScreenshots(command.screenshots());
            if (command.achievements() != null) existingGame.setAchievements(command.achievements());

            if(gamesUpdatePort.update(existingGame)) return gameId;
        }
        return null;
    }

    private GameId generateUniqueIdFromDeveloperAndTitle(DeveloperId developerId, String title) {
        return new GameId(UUID.nameUUIDFromBytes((developerId.toString()+":"+title).getBytes(StandardCharsets.UTF_8)));
    }
}
