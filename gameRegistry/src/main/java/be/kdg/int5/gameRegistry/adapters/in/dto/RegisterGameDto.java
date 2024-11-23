package be.kdg.int5.gameRegistry.adapters.in.dto;

import be.kdg.int5.gameRegistry.domain.Rule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class RegisterGameDto {
    @NotNull
    private String title;
    @NotNull
    private String currentHost;
    private String description;
    private BigDecimal currentPrice;
    private String iconUrl;
    private String backgroundUrl;
    private Set<Rule> rules;
    @Valid
    private Set<RegisterAchievementDto> achievements;
    private List<String> screenshots;

    public RegisterGameDto() {
    }

    public RegisterGameDto(
            String title,
            String currentHost,
            String description,
            BigDecimal currentPrice,
            String iconUrl,
            String backgroundUrl,
            Set<Rule> rules,
            Set<RegisterAchievementDto> achievements,
            List<String> screenshots
    ) {
        this.title = title;
        this.currentHost = currentHost;
        this.description = description;
        this.currentPrice = currentPrice;
        this.iconUrl = iconUrl;
        this.backgroundUrl = backgroundUrl;
        this.rules = rules;
        this.achievements = achievements;
        this.screenshots = screenshots;
    }


    public @NotNull String getTitle() {
        return title;
    }

    public @NotNull String getCurrentHost() {
        return currentHost;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Set<RegisterAchievementDto> getAchievements() {
        return achievements;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }
}
