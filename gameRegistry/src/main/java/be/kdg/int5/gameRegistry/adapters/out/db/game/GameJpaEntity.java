package be.kdg.int5.gameRegistry.adapters.out.db.game;

import be.kdg.int5.gameRegistry.adapters.out.db.achievement.AchievementJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.developer.DeveloperJpaEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "game")
public class GameJpaEntity {
    @Id
    private UUID id;
    private String title;
    private String description;
    private BigDecimal currentPrice;
    private String currentHost;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "url", column = @Column(name = "icon_url"))
    })
    private ImageResourceJpaEmbeddable icon;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "url", column = @Column(name = "background_url"))
    })
    private ImageResourceJpaEmbeddable background;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            schema = "game_registry", name = "game_rules",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private Set<RuleJpaEmbeddable> rules;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            schema = "game_registry", name = "game_screenshots",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private Set<ImageResourceJpaEmbeddable> screenshots;

    @ManyToOne
    private DeveloperJpaEntity developer;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private Set<AchievementJpaEntity> achievements;

    public GameJpaEntity() {
    }

    public GameJpaEntity(
            UUID id,
            String title,
            String description,
            BigDecimal currentPrice,
            ImageResourceJpaEmbeddable icon,
            ImageResourceJpaEmbeddable background,
            Set<RuleJpaEmbeddable> rules,
            String currentHost,
            DeveloperJpaEntity developer,
            Set<ImageResourceJpaEmbeddable> screenshots,
            Set<AchievementJpaEntity> achievements
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.currentPrice = currentPrice;
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

    public void setId(UUID gameId) {
        this.id = gameId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public ImageResourceJpaEmbeddable getIcon() {
        return icon;
    }

    public ImageResourceJpaEmbeddable getBackground() {
        return background;
    }

    public Set<RuleJpaEmbeddable> getRules() {
        return rules;
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public DeveloperJpaEntity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperJpaEntity developer) {
        this.developer = developer;
    }

    public Set<ImageResourceJpaEmbeddable> getScreenshots() {
        return screenshots;
    }

    public Set<AchievementJpaEntity> getAchievements() {
        return achievements;
    }
}
