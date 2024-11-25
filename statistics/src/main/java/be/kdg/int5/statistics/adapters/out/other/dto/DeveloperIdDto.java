package be.kdg.int5.statistics.adapters.out.other.dto;

import java.util.UUID;

public class DeveloperIdDto {
    private UUID uuid;

    public DeveloperIdDto() {
    }

    public DeveloperIdDto(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
