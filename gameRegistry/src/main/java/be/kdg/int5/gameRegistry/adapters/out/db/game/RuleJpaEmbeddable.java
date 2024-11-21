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

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

}
