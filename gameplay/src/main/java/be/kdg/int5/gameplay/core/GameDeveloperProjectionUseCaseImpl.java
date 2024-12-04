package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameDeveloperProjection;
import be.kdg.int5.gameplay.port.in.GameDeveloperProjectionUseCase;
import be.kdg.int5.gameplay.port.out.GameDeveloperProjectionSavePort;
import org.springframework.stereotype.Service;

@Service
public class GameDeveloperProjectionUseCaseImpl implements GameDeveloperProjectionUseCase {
    private final GameDeveloperProjectionSavePort gameDeveloperProjectionSavePort;

    public GameDeveloperProjectionUseCaseImpl(GameDeveloperProjectionSavePort gameDeveloperProjectionSavePort) {
        this.gameDeveloperProjectionSavePort = gameDeveloperProjectionSavePort;
    }

    @Override
    public void project(GameDeveloperProjection projection) {
        gameDeveloperProjectionSavePort.save(projection);
    }
}
