package be.kdg.int5.gameRegistry.core;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import be.kdg.int5.gameRegistry.port.out.LoadGamesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameListQueryImpl implements GameListQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameListQueryImpl.class);

    private final LoadGamesPort loadGamesPort;

    public GameListQueryImpl(LoadGamesPort loadGamesPort) {
        this.loadGamesPort = loadGamesPort;
    }

    @Override
    public Optional<List<Game>> retrieveGameList() {

        LOGGER.info("Retrieving all games...");
        return loadGamesPort.loadAllGames();
    }
}
