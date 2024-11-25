package be.kdg.int5.statistics.adapters.in.dto;
import jakarta.validation.constraints.NotNull;

import java.util.*;

import java.util.List;
import java.util.UUID;

public class PlayerGameStatsDto {
    @NotNull
    private final UUID playerId;

    @NotNull
    private final UUID gameId;
    private final List<CompletedSessionDto> completedSessions;
    private final List<AchievementProgressDto> achievementProgress;

    public PlayerGameStatsDto(
            UUID playerId,
            UUID gameId,
            List<CompletedSessionDto> completedSessions,
            List<AchievementProgressDto> achievementProgress
    ) {
        this.playerId = Objects.requireNonNull(playerId);
        this.gameId = Objects.requireNonNull(gameId);
        this.completedSessions = Objects.requireNonNull(completedSessions);
        this.achievementProgress = Objects.requireNonNull(achievementProgress);
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public List<CompletedSessionDto> getCompletedSessions() {
        return completedSessions;
    }

    public List<AchievementProgressDto> getAchievementProgress() {
        return achievementProgress;
    }
}

