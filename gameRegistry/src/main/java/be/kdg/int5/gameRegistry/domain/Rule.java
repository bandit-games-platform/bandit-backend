package be.kdg.int5.gameRegistry.domain;

import java.util.Objects;

public record Rule(int stepNumber, String rule) {

    public Rule {
        assert stepNumber >= 0;

        assert rule != null && !rule.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule r)) return false;
        return stepNumber == r.stepNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stepNumber);
    }
}
