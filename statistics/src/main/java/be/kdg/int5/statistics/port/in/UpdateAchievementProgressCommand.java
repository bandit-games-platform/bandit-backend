package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.AchievementId;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;

import java.util.Objects;

public record UpdateAchievementProgressCommand(
        PlayerId playerId,
        GameId gameId,
        AchievementId achievementId,
        Integer newAmount
) {
    public UpdateAchievementProgressCommand {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(achievementId);
    }
}
