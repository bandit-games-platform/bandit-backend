package be.kdg.int5.gameRegistry.adapters.out.db.game;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "game_screenshot")
public class GameScreenshotJpaEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "game", referencedColumnName = "game_id")
    private GameJpaEntity game;
    @OneToOne
    private ImageResourceJpaEntity screenshot;

    public GameScreenshotJpaEntity() {
    }

    public GameScreenshotJpaEntity(UUID id, GameJpaEntity gameJpaEntity, ImageResourceJpaEntity screenshot) {
        this.id = id;
        this.game = gameJpaEntity;
        this.screenshot = screenshot;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameJpaEntity getGameJpaEntity() {
        return game;
    }

    public void setGameJpaEntity(GameJpaEntity gameJpaEntity) {
        this.game = gameJpaEntity;
    }

    public ImageResourceJpaEntity getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(ImageResourceJpaEntity screenshot) {
        this.screenshot = screenshot;
    }
}
