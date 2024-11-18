package be.kdg.int5.gameRegistry.adapters.in.dto;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LoadGameDto {
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
    private String icon;
    private String background;
    private Set<LoadRuleDto> rules;
    private String currentHost;
    private DeveloperDto developer;

    private List<String> screenshots;
    private Set<LoadAchievementDto> achievements;

    public LoadGameDto() {
    }

    public LoadGameDto(UUID id, String title, String description, String icon) {
        this(id, title, description, icon, "", Set.of(), "", List.of(), Set.of());
    }

    public LoadGameDto(
            UUID id,
            String title,
            String description,
            BigDecimal price,
            String background,
            DeveloperDto developer,
            List<String> screenshots
    ) {
        this(
                id,
                title,
                description,
                price,
                null,
                background,
                null,
                null,
                developer,
                screenshots,
                null
        );
    }

    public LoadGameDto(
            UUID id,
            String title,
            String description,
            String icon,
            String background,
            Set<LoadRuleDto> rules,
            String currentHost,
            List<String> screenshots,
            Set<LoadAchievementDto> achievements
    ) {
        this(
                id,
                title,
                description,
                null,
                icon,
                background,
                rules,
                currentHost,
                null,
                screenshots,
                achievements
        );
    }

    public LoadGameDto(
            UUID id,
            String title,
            String description,
            BigDecimal price,
            String icon,
            String background,
            Set<LoadRuleDto> rules,
            String currentHost,
            DeveloperDto developer,
            List<String> screenshots,
            Set<LoadAchievementDto> achievements
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.icon = icon;
        this.background = background;
        this.rules = rules;
        this.currentHost = currentHost;
        this.developer = developer;
        this.screenshots = screenshots;
        this.achievements = achievements;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Set<LoadRuleDto> getRules() {
        return rules;
    }

    public void setRules(Set<LoadRuleDto> rules) {
        this.rules = rules;
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public void setCurrentHost(String currentHost) {
        this.currentHost = currentHost;
    }

    public DeveloperDto getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperDto developer) {
        this.developer = developer;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    public Set<LoadAchievementDto> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<LoadAchievementDto> achievements) {
        this.achievements = achievements;
    }
}
