package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.port.in.VerifyIfAchievementBelongsToGameCommand;
import be.kdg.int5.statistics.port.in.VerifyIfAchievementBelongsToGameUseCase;
import be.kdg.int5.statistics.port.out.GameRegistryCallPort;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfAchievementBelongsToGameUseCaseImpl implements VerifyIfAchievementBelongsToGameUseCase {
    private final GameRegistryCallPort gameRegistryCallPort;

    public VerifyIfAchievementBelongsToGameUseCaseImpl(GameRegistryCallPort gameRegistryCallPort) {
        this.gameRegistryCallPort = gameRegistryCallPort;
    }

    @Override
    public boolean doesAchievementBelongToGame(VerifyIfAchievementBelongsToGameCommand command) {
        return gameRegistryCallPort.doesAchievementBelongToGame(command.achievementId(), command.gameId());
    }
}
