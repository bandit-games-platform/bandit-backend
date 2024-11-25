package be.kdg.int5.gameRegistry.core.query;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GameDetailsQuery;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameDetailsQueryImpl implements GameDetailsQuery {
    private final GamesLoadPort gamesLoadPort;

    public GameDetailsQueryImpl(GamesLoadPort gamesLoadPort) {
        this.gamesLoadPort = gamesLoadPort;
    }

    @Override
    public Game getDetailsForGameFromId(UUID gameId) {
        return gamesLoadPort.loadGameByIdWithDetails(gameId);
    }

    @Override
    public Game getGameWithoutRelationshipsFromId(UUID gameId) {
        return gamesLoadPort.loadGameById(gameId);
    }

    @Override
    public Game getGameWithAchievementsFromId(UUID gameId) {
        return gamesLoadPort.loadGameByIdWithAchievements(gameId);
    }
}
