package be.kdg.int5.gameRegistry.adapters.in.dto;

import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.AchievementId;

import java.util.UUID;

public class AchievementDto {
    private UUID id;
    private String title;
    private int counterTotal;
    private String description;

    public AchievementDto() {
    }

    public AchievementDto(UUID id, String title, int counterTotal, String description) {
        this.id = id;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public String getDescription() {
        return description;
    }

    public Achievement mapToObject() {
        return new Achievement(
                new AchievementId(getId()),
                getTitle(),
                getCounterTotal(),
                getDescription()
        );
    }
}
