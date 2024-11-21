package be.kdg.int5.gameRegistry.adapters.in.dto;

import java.util.UUID;

public class LoadDeveloperIdDto {
    private UUID uuid;

    public LoadDeveloperIdDto() {
    }

    public LoadDeveloperIdDto(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
