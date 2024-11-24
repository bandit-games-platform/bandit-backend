package be.kdg.int5.statistics.adapters.in.dto;

import java.util.List;
import java.util.UUID;

public class GameProgressDto {
    UUID gameId;
    List<AchievementProgressDTO> achievementProgresses;

    public GameProgressDto() {
    }

    public GameProgressDto(UUID gameId, List<AchievementProgressDTO> achievementProgresses) {
        this.gameId = gameId;
        this.achievementProgresses = achievementProgresses;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public List<AchievementProgressDTO> getAchievementProgresses() {
        return achievementProgresses;
    }

    public void setAchievementProgresses(List<AchievementProgressDTO> achievementProgresses) {
        this.achievementProgresses = achievementProgresses;
    }
}
