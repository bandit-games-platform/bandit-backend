package be.kdg.int5.gameRegistry.adapters.out.db.game;

import be.kdg.int5.gameRegistry.adapters.out.db.achievement.AchievementJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.rule.RuleJpaEntity;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "game")
public class GameJpaEntity {
    @Id
    @Column(name = "game_id")
    private UUID gameId;
    private String title;
    private String description;
    private BigDecimal currentPrice;
    @OneToOne(fetch = FetchType.LAZY)
    private ImageResourceJpaEntity icon;
    @OneToOne(fetch = FetchType.LAZY)
    private ImageResourceJpaEntity background;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private Set<RuleJpaEntity> rules;
    private String currentHost;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private Set<GameScreenshotJpaEntity> screenshots;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private Set<AchievementJpaEntity> achievements;

    public GameJpaEntity() {
    }

    public GameJpaEntity(UUID gameId, String title, String description, BigDecimal currentPrice, ImageResourceJpaEntity icon, ImageResourceJpaEntity background, Set<RuleJpaEntity> rules, String currentHost, Set<GameScreenshotJpaEntity> screenshots, Set<AchievementJpaEntity> achievements) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.currentPrice = currentPrice;
        this.icon = icon;
        this.background = background;
        this.rules = rules;
        this.currentHost = currentHost;
        this.screenshots = screenshots;
        this.achievements = achievements;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ImageResourceJpaEntity getIcon() {
        return icon;
    }

    public void setIcon(ImageResourceJpaEntity icon) {
        this.icon = icon;
    }

    public ImageResourceJpaEntity getBackground() {
        return background;
    }

    public void setBackground(ImageResourceJpaEntity background) {
        this.background = background;
    }

    public Set<RuleJpaEntity> getRules() {
        return rules;
    }

    public void setRules(Set<RuleJpaEntity> rules) {
        this.rules = rules;
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public void setCurrentHost(String currentHost) {
        this.currentHost = currentHost;
    }

    public Set<GameScreenshotJpaEntity> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(Set<GameScreenshotJpaEntity> screenshots) {
        this.screenshots = screenshots;
    }

    public Set<AchievementJpaEntity> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<AchievementJpaEntity> achievements) {
        this.achievements = achievements;
    }
}
