package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Game;

import java.util.List;
import java.util.Optional;

public interface LoadGamesPort {

    List<Game> loadAllGamesByTitleWithIcon(String title);
}
