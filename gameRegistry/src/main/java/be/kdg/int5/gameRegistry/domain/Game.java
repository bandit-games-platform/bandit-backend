package be.kdg.int5.gameRegistry.domain;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.adapters.out.db.achievement.AchievementJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.game.GameScreenshotJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.game.ImageResourceJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.rule.RuleJpaEntity;

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

    public Game( String title, String description, ImageResource icon, ImageResource background, Set<Rule> rules, ResourceURL currentHost, List<ImageResource> screenshots, Set<Achievement> achievements) {
        this(new GameId(UUID.randomUUID()),title, description, icon, background, rules, currentHost, screenshots,achievements);
    }

    public Game(GameId id, String title, String description, ImageResource icon, ImageResource background, Set<Rule> rules, ResourceURL currentHost, List<ImageResource> screenshots, Set<Achievement> achievements) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.icon = Objects.requireNonNull(icon);
        this.background = Objects.requireNonNull(background);
        this.rules = Objects.requireNonNull(rules);
        this.currentHost = Objects.requireNonNull(currentHost);

        this.screenshots = Objects.requireNonNullElse(screenshots, new ArrayList<>());
        this.achievements = Objects.requireNonNullElse(achievements, new HashSet<>());
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
