package be.kdg.int5.gameRegistry.port.in.query;

import be.kdg.int5.gameRegistry.domain.Achievement;

import java.util.List;
import java.util.UUID;

public interface GetGameAchievementsQuery {
    List<Achievement> getAchievementsForGameFromId(UUID gameId);
}