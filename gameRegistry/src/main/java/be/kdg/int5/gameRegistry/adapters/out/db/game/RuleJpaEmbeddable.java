package be.kdg.int5.gameRegistry.adapters.out.db.game;
import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleJpaEmbeddable that)) return false;
        return getStepNumber() == that.getStepNumber() && Objects.equals(getRule(), that.getRule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStepNumber(), getRule());
    }
}
