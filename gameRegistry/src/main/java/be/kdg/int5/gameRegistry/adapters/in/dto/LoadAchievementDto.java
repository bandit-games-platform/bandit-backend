package be.kdg.int5.gameRegistry.adapters.in.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class LoadAchievementDto {

    @NotNull
    private UUID id;
    @NotNull
    private String title;
    @Min(value = 1, message = "counterTotal must be greater than 0")
    private int counterTotal;
    @NotNull
    private String description;

    public LoadAchievementDto() {
    }

    public LoadAchievementDto(UUID id, String title, int counterTotal, String description) {
        this.id = id;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public void setId(@NotNull UUID id) {
        this.id = id;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @Min(value = 1, message = "counterTotal must be greater than 0")
    public int getCounterTotal() {
        return counterTotal;
    }

    public void setCounterTotal(@Min(value = 1, message = "counterTotal must be greater than 0") int counterTotal) {
        this.counterTotal = counterTotal;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }
}
