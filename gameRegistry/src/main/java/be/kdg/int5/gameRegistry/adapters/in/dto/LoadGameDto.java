package be.kdg.int5.gameRegistry.adapters.in.dto;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LoadGameDto {

    @NotNull
    private UUID id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    private String icon;
    private String background;
    private Set<LoadRuleDto> rules;
    private String currentHost;

    private List<String> screenshots;
    private Set<LoadAchievementDto> achievements;

    public LoadGameDto() {
    }

    public LoadGameDto(String title, String description, String icon, UUID id) {
        this(id, title, description, icon,
                ""
                ,Set.of()
                ,""
                , List.of()
                ,Set.of());
    }

    public LoadGameDto(UUID id, String title, String description, String icon, String background, Set<LoadRuleDto> rules, String currentHost, List<String> screenshots, Set<LoadAchievementDto> achievements) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.background = background;
        this.rules = rules;
        this.currentHost = currentHost;
        this.screenshots = screenshots;
        this.achievements = achievements;
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

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
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
