package be.kdg.int5.statistics.adapters.in.dto;

import java.util.*;

public class AchievementProgressDto {
    private final UUID achievementId;
    private final int counterValue;

    public AchievementProgressDto(UUID achievementId, int counterValue) {
        this.achievementId = Objects.requireNonNull(achievementId);
        this.counterValue = counterValue;
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public int getCounterValue() {
        return counterValue;
    }
}

