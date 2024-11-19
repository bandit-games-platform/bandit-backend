package be.kdg.int5.statistics.adapters.in.dto;

import java.util.Objects;
import java.util.UUID;

public class AchievementDTO {
    private final UUID achievementId;
    private final UUID gameId;
    private final int counterTotal;
    private final String description;
    private final String title;

    public AchievementDTO(UUID achievementId, UUID gameId, int counterTotal, String description, String title) {
        this.achievementId = Objects.requireNonNull(achievementId);
        this.gameId = gameId;
        this.counterTotal = counterTotal;
        this.description = description;
        this.title = title;
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }


}

