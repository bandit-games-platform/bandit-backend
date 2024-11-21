package be.kdg.int5.gameRegistry.port.in.query;

import be.kdg.int5.gameRegistry.domain.Game;

import java.util.UUID;

public interface GetGameDetailsQuery {
    Game getDetailsForGameFromId(UUID gameId);
    Game getGameWithoutRelationshipsFromId(UUID gameId);
    Game getGameWithAchievementsFromId(UUID gameId);
}
