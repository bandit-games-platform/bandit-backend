package be.kdg.int5.gameRegistry.core;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameListQueryImpl implements GameListQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameListQueryImpl.class);

    private final GamesLoadPort gamesLoadPort;

    public GameListQueryImpl(GamesLoadPort gamesLoadPort) {
        this.gamesLoadPort = gamesLoadPort;
    }

    @Override
    public List<Game> retrieveGamesWithIcon() {

        LOGGER.info("Retrieving all games...");
        return gamesLoadPort.loadAllGamesWithIcon();
    }
}
