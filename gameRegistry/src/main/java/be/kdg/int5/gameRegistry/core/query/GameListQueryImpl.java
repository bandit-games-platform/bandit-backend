package be.kdg.int5.gameRegistry.core.query;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.domain.GameId;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GameListQueryImpl implements GameListQuery {
    private final GamesLoadPort gamesLoadPort;

    public GameListQueryImpl(GamesLoadPort gamesLoadPort) {
        this.gamesLoadPort = gamesLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> retrieveGamesWithIcon() {
        return gamesLoadPort.loadAllGamesWithIcon();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> retrieveGamesByTitleLikeAndPriceBelowWithIcon(String title, BigDecimal price) {
        return gamesLoadPort.loadAllGamesByTitleLikeAndPriceBelowWithIcon(title,price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getGamesByIdInList(List<GameId> gameIds) {
        return gamesLoadPort.loadAllGamesWithIdInList(gameIds);
    }
}
