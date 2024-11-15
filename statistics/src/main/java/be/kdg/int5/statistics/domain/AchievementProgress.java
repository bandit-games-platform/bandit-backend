package be.kdg.int5.statistics.domain;

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
}
