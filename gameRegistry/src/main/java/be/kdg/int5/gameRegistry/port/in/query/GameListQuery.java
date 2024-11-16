package be.kdg.int5.gameRegistry.port.in.query;

import be.kdg.int5.gameRegistry.domain.Game;

import java.util.List;

public interface GameListQuery {

    List<Game> retrieveGamesWithIcon();

    List<Game> retrieveGamesByTitleLikeWithIcon(String title);
}
