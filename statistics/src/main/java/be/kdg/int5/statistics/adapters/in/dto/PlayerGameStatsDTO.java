package be.kdg.int5.statistics.adapters.in.dto;
import java.util.*;

import java.util.List;
import java.util.UUID;

public class PlayerGameStatsDTO {

    private final UUID playerId;
    private final UUID gameId;
    private final List<CompletedSessionDTO> completedSessions;
    private final List<AchievementProgressDTO> achievementProgress;

    public PlayerGameStatsDTO(
            UUID playerId,
            UUID gameId,
            List<CompletedSessionDTO> completedSessions,
            List<AchievementProgressDTO> achievementProgress
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

    public List<CompletedSessionDTO> getCompletedSessions() {
        return completedSessions;
    }

    public List<AchievementProgressDTO> getAchievementProgress() {
        return achievementProgress;
    }
}

