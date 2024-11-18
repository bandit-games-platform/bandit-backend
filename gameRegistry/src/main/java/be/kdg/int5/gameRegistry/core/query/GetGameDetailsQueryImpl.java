package be.kdg.int5.gameRegistry.core.query;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GetGameDetailsQuery;
import be.kdg.int5.gameRegistry.port.out.LoadGamesPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetGameDetailsQueryImpl implements GetGameDetailsQuery {
    private final LoadGamesPort loadGamesPort;

    public GetGameDetailsQueryImpl(LoadGamesPort loadGamesPort) {
        this.loadGamesPort = loadGamesPort;
    }

    @Override
    public Game getDetailsForGameFromId(UUID gameId) {
        return loadGamesPort.loadGameByIdWithDetails(gameId);
    }
}
