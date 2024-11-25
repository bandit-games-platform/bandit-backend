package be.kdg.int5.gameRegistry.adapters.in.dto;

import java.util.UUID;

public class LoadAchievementDto {
    private UUID id;
    private String title;
    private int counterTotal;
    private String description;

    public LoadAchievementDto() {
    }

    public LoadAchievementDto(UUID id, String title, int counterTotal, String description) {
        this.id = id;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public void setCounterTotal(int counterTotal) {
        this.counterTotal = counterTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
