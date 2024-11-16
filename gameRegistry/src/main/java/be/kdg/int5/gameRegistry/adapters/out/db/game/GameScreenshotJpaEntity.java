package be.kdg.int5.gameRegistry.adapters.out.db.game;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "game_screenshot")
public class GameScreenshotJpaEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "screenshots")
    private GameJpaEntity gameJpaEntity;
    @OneToOne
    private ImageResourceJpaEntity screenshot;

    public GameScreenshotJpaEntity() {
    }

    public GameScreenshotJpaEntity(UUID id, GameJpaEntity gameJpaEntity, ImageResourceJpaEntity screenshot) {
        this.id = id;
        this.gameJpaEntity = gameJpaEntity;
        this.screenshot = screenshot;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameJpaEntity getGameJpaEntity() {
        return gameJpaEntity;
    }

    public void setGameJpaEntity(GameJpaEntity gameJpaEntity) {
        this.gameJpaEntity = gameJpaEntity;
    }

    public ImageResourceJpaEntity getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(ImageResourceJpaEntity screenshot) {
        this.screenshot = screenshot;
    }
}
