package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.port.in.VerifyIfDeveloperOwnsGameCommand;
import be.kdg.int5.statistics.port.in.VerifyIfDeveloperOwnsGameUseCase;
import be.kdg.int5.statistics.port.out.GameRegistryCallPort;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfDeveloperOwnsGameUseCaseImpl implements VerifyIfDeveloperOwnsGameUseCase {
    private final GameRegistryCallPort gameRegistryCallPort;

    public VerifyIfDeveloperOwnsGameUseCaseImpl(GameRegistryCallPort gameRegistryCallPort) {
        this.gameRegistryCallPort = gameRegistryCallPort;
    }

    @Override
    public boolean doesDeveloperOwnGame(VerifyIfDeveloperOwnsGameCommand command) {
        return gameRegistryCallPort.doesDeveloperOwnGame(command.developerId(), command.gameId());
    }
}
