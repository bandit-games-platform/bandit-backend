package be.kdg.int5.gameRegistry.port.in.query;

import be.kdg.int5.gameRegistry.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameListQuery {

    List<Game> retrieveGamesWithIcon();
}
