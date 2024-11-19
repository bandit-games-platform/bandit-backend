package be.kdg.int5.chatbot.adapters.out.gameRule;

import be.kdg.int5.chatbot.adapters.out.gameDetails.GameDetailsJpaEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "game_rule")
public class GameRuleJpaEntity {
    @Id
    private UUID uuid;
    private int stepNumber;
    private String rule;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_details", referencedColumnName = "game_id")
    private GameDetailsJpaEntity gameDetails;

    public GameRuleJpaEntity() {
    }

    public GameRuleJpaEntity(UUID uuid, int stepNumber, String rule, GameDetailsJpaEntity gameDetails) {
        this.uuid = UUID.randomUUID();
        this.stepNumber = stepNumber;
        this.rule = rule;
        this.gameDetails = gameDetails;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public GameDetailsJpaEntity getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(GameDetailsJpaEntity gameDetails) {
        this.gameDetails = gameDetails;
    }
}
