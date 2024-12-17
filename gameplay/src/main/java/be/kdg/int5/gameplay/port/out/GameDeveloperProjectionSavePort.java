package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.GameDeveloperProjection;

public interface GameDeveloperProjectionSavePort {
    void save(GameDeveloperProjection gameDeveloperProjection);
}
