package be.kdg.int5.gameRegistry.adapters.in.dto;

import java.util.UUID;

public class LoadAchievementIdDto {
    private UUID uuid;

    public LoadAchievementIdDto() {
    }

    public LoadAchievementIdDto(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
