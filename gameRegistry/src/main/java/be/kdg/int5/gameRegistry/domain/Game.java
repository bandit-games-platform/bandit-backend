package be.kdg.int5.gameRegistry.domain;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;

import java.util.*;

public class Game {
    private final GameId id;
    private final String title;
    private String description;
    private ImageResource icon;
    private ImageResource background;
    private final Set<Rule> rules;
    private ResourceURL currentHost;

    private List<ImageResource> screenshots;
    private Set<Achievement> achievements;

    public Game(GameId id, String title, String description, ImageResource icon, ImageResource background, Set<Rule> rules, ResourceURL currentHost, List<ImageResource> screenshots, Set<Achievement> achievements) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(icon);
        Objects.requireNonNull(background);
        Objects.requireNonNull(rules);
        Objects.requireNonNull(currentHost);

        if (screenshots == null) screenshots = new ArrayList<>();
        if (achievements == null) achievements = new HashSet<>();

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


    public GameId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageResource getIcon() {
        return icon;
    }

    public void setIcon(ImageResource icon) {
        this.icon = icon;
    }

    public ImageResource getBackground() {
        return background;
    }

    public void setBackground(ImageResource background) {
        this.background = background;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public ResourceURL getCurrentHost() {
        return currentHost;
    }

    public void setCurrentHost(ResourceURL currentHost) {
        this.currentHost = currentHost;
    }

    public List<ImageResource> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<ImageResource> screenshots) {
        this.screenshots = screenshots;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }
}
