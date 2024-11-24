package be.kdg.int5.gameRegistry.core;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.domain.*;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegisterGameUseCaseImpl implements RegisterGameUseCase {
    private final Logger logger = LoggerFactory.getLogger(RegisterGameUseCaseImpl.class);

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
    @Transactional
    public GameId registerGame(RegisterGameCommand command) {
        GameId gameId = Game.generateUniqueIdFromDeveloperAndTitle(command.developerId(), command.title());
        logger.info("gameRegistry:register-game Generated gameId is '{}' for title '{}' and developerId '{}'",
                gameId,
                command.title(),
                command.developerId()
        );

        Set<Achievement> achievements = mapAchievementRecordsToDomainSet(command.achievements(), gameId);

        Game existingGame = gamesLoadPort.loadGameByIdWithDetails(gameId.uuid());

        if (existingGame == null) {
            //Creation mode
            Developer gameDev = developerLoadPort.load(command.developerId());
            if (gameDev == null) gameDev = new Developer(command.developerId(), command.developerId().toString());
            logger.info("gameRegistry:register-game [CREATION MODE] developer name is '{}'", gameDev.studioName());

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
                    achievements
            );

            if(gamesCreatePort.create(newGame)) return gameId;
        }else {
            //Patch mode
            logger.info("gameRegistry:register-game [PATCH MODE] for existing game: '{}'", existingGame);

            existingGame.patch(
                    command.description(),
                    command.currentPrice(),
                    command.icon() == null ? null : new ImageResource(new ResourceURL(command.icon())),
                    command.background() == null ? null : new ImageResource(new ResourceURL(command.background())),
                    command.rules(),
                    command.currentHost(),
                    command.screenshots(),
                    achievements
            );

            if(gamesUpdatePort.update(existingGame)) return gameId;
        }
        return null;
    }

    private Set<Achievement> mapAchievementRecordsToDomainSet(List<RegisterGameCommand.AchievementRecord> achievements, GameId gameId) {
        if (achievements == null) return null;

        return achievements
                .stream()
                .map(ar -> mapAchievementCommandRecordToDomain(ar, gameId))
                .collect(Collectors.toSet());
    }

    private Achievement mapAchievementCommandRecordToDomain(RegisterGameCommand.AchievementRecord achievementRecord, GameId gameId) {
        return new Achievement(
                Achievement.generateUniqueIdFromGameIdAndUniqueNumber(gameId, achievementRecord.uniqueNumber()),
                achievementRecord.title(),
                achievementRecord.counterTotal(),
                achievementRecord.description()
        );
    }
}
