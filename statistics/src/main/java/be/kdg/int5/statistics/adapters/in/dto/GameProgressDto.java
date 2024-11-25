package be.kdg.int5.statistics.adapters.in.dto;

import java.util.List;
import java.util.UUID;

public class GameProgressDto {
    UUID gameId;
    List<AchievementProgressDto> achievementProgresses;

    public GameProgressDto() {
    }

    public GameProgressDto(UUID gameId, List<AchievementProgressDto> achievementProgresses) {
        this.gameId = gameId;
        this.achievementProgresses = achievementProgresses;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public List<AchievementProgressDto> getAchievementProgresses() {
        return achievementProgresses;
    }

    public void setAchievementProgresses(List<AchievementProgressDto> achievementProgresses) {
        this.achievementProgresses = achievementProgresses;
    }
}
