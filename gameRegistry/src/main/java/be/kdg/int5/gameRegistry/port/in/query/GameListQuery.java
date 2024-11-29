package be.kdg.int5.gameRegistry.port.in.query;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.domain.GameId;

import java.math.BigDecimal;
import java.util.List;

public interface GameListQuery {
    List<Game> retrieveGamesWithIcon();
    List<Game> retrieveGamesByTitleLikeAndPriceBelowWithIcon(String title, BigDecimal price);
    List<Game> getGamesByIdInList(List<GameId> gameIds);
}
