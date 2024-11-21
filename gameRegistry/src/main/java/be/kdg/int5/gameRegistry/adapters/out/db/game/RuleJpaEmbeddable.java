package be.kdg.int5.gameRegistry.adapters.out.db.game;
import jakarta.persistence.*;

@Embeddable
public class RuleJpaEmbeddable {
    private int stepNumber ;
    private String rule;

    public RuleJpaEmbeddable() {
    }

    public RuleJpaEmbeddable(int stepNumber, String rule) {
        this.stepNumber = stepNumber;
        this.rule = rule;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getRule() {
        return rule;
    }
}
