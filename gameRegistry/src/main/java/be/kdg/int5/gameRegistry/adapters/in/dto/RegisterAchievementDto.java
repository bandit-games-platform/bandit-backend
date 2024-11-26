package be.kdg.int5.gameRegistry.adapters.in.dto;

import be.kdg.int5.gameRegistry.port.in.RegisterGameCommand;
import jakarta.validation.constraints.NotNull;

public class RegisterAchievementDto {
    @NotNull
    private Integer uniqueNumber;
    @NotNull
    private String title;
    private int counterTotal;
    @NotNull
    private String description;

    public RegisterAchievementDto() {
    }

    public RegisterAchievementDto(int uniqueNumber, String title, int counterTotal, String description) {
        this.uniqueNumber = uniqueNumber;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
    }


    public Integer getUniqueNumber() {
        return uniqueNumber;
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

    public RegisterGameCommand.AchievementRecord mapToCommandObject() {
        return new RegisterGameCommand.AchievementRecord(
                getUniqueNumber(),
                getTitle(),
                getCounterTotal(),
                getDescription()
        );
    }
}
