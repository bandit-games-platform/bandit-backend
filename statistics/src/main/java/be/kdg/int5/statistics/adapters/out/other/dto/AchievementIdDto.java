package be.kdg.int5.statistics.adapters.out.other.dto;

import java.util.UUID;

public class AchievementIdDto {
    private UUID uuid;

    public AchievementIdDto() {
    }

    public AchievementIdDto(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
