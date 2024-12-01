package be.kdg.int5.statistics.adapters.in.dto;

import java.util.List;
import java.util.UUID;

public class GameProgressDto {
    private UUID gameId;
    private long wins;
    private long losses;
    private long draws;
    private List<AchievementProgressDto> achievementProgresses;

    public GameProgressDto() {
    }

    public GameProgressDto(UUID gameId, long wins, long losses, long draws, List<AchievementProgressDto> achievementProgresses) {
        this.gameId = gameId;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.achievementProgresses = achievementProgresses;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getLosses() {
        return losses;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public List<AchievementProgressDto> getAchievementProgresses() {
        return achievementProgresses;
    }

    public void setAchievementProgresses(List<AchievementProgressDto> achievementProgresses) {
        this.achievementProgresses = achievementProgresses;
    }
}
