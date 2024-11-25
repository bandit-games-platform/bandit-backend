package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Game;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GamesLoadPort {
    Game loadGameByIdWithDetails(UUID gameId);

    List<Game> loadAllGamesWithIcon();

    List<Game> loadAllGamesByTitleLikeAndPriceBelowWithIcon(String title, BigDecimal maxPrice);

}
