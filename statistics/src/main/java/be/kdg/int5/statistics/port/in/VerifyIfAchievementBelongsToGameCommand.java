package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.AchievementId;
import be.kdg.int5.statistics.domain.GameId;

public record VerifyIfAchievementBelongsToGameCommand(GameId gameId, AchievementId achievementId) {
}
