package be.kdg.int5.gameplay.port.in.query;

import be.kdg.int5.gameplay.domain.GameId;

public interface TitleForGameIdQuery {
    String getGameTitleForGameIdInProjection(GameId gameId);
}
