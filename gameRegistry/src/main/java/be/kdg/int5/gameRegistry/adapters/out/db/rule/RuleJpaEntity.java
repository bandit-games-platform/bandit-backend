package be.kdg.int5.gameRegistry.adapters.out.db.rule;
import be.kdg.int5.gameRegistry.adapters.out.db.game.GameJpaEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "rule")
public class RuleJpaEntity {
    @Id
    private UUID uuid;
    private int stepNumber ;
    private String rule;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "game", referencedColumnName = "game_id")
    private GameJpaEntity game;

    public RuleJpaEntity() {
    }

    public RuleJpaEntity(int stepNumber, String rule, GameJpaEntity game) {
        this(UUID.randomUUID(), stepNumber, rule, game);
    }

    public RuleJpaEntity(UUID uuid, int stepNumber, String rule, GameJpaEntity game) {
        this.uuid = uuid;
        this.stepNumber = stepNumber;
        this.rule = rule;
        this.game = game;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }
}
