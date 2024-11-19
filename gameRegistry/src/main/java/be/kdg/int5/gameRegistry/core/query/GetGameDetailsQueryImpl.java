package be.kdg.int5.gameRegistry.core.query;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GetGameDetailsQuery;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetGameDetailsQueryImpl implements GetGameDetailsQuery {
    private final GamesLoadPort gamesLoadPort;

    public GetGameDetailsQueryImpl(GamesLoadPort gamesLoadPort) {
        this.gamesLoadPort = gamesLoadPort;
    }

    @Override
    public Game getDetailsForGameFromId(UUID gameId) {
        return gamesLoadPort.loadGameByIdWithDetails(gameId);
    }
}
