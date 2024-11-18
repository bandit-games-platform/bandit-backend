package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadGamesPort {
    Game loadGameByIdWithDetails(UUID gameId);

    List<Game> loadAllGamesWithIcon();
}
