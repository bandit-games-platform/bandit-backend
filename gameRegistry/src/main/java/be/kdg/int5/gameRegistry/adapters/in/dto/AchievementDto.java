package be.kdg.int5.gameRegistry.adapters.in.dto;

import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.AchievementId;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AchievementDto {
    @NotNull
    private UUID id;

    private UUID gameId;

    @NotNull
    private String title;
    private int counterTotal;
    @NotNull
    private String description;

    public AchievementDto() {
    }

    public AchievementDto(UUID id, UUID gameId, String title, int counterTotal, String description) {
        this.id = id;
        this.gameId = gameId;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
    }


    public UUID getId() {
        return id;
    }

    public UUID getGameId() {
        return gameId;
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
