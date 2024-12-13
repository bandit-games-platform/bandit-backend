package be.kdg.int5.gameplay.core.query;

import be.kdg.int5.gameplay.domain.GameDeveloperProjection;
import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.port.in.query.TitleForGameIdQuery;
import be.kdg.int5.gameplay.port.out.GameDeveloperProjectionLoadPort;
import org.springframework.stereotype.Service;

@Service
public class TitleForGameIdQueryImpl implements TitleForGameIdQuery {
    private final GameDeveloperProjectionLoadPort gameDeveloperProjectionLoadPort;

    public TitleForGameIdQueryImpl(GameDeveloperProjectionLoadPort gameDeveloperProjectionLoadPort) {
        this.gameDeveloperProjectionLoadPort = gameDeveloperProjectionLoadPort;
    }

    @Override
    public String getGameTitleForGameIdInProjection(GameId gameId) {
        GameDeveloperProjection projection = gameDeveloperProjectionLoadPort.loadByGameId(gameId);
        if (projection == null) return null;

        return projection.gameTitle();
    }
}
