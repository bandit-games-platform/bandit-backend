package be.kdg.int5.statistics.domain;

import java.util.Objects;

public class AchievementProgress {
    private final AchievementId achievementId;
    private final int counterValue;

    public AchievementProgress(final AchievementId achievementId, final int counterValue) {
        this.achievementId = achievementId;
        this.counterValue = counterValue;
    }

    public AchievementId getAchievementId() {
        return achievementId;
    }

    public int getCounterValue() {
        return counterValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AchievementProgress that)) return false;
        return Objects.equals(achievementId, that.achievementId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(achievementId);
    }
}
