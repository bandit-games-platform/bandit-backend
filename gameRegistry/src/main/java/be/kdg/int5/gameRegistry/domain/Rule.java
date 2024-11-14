package be.kdg.int5.gameRegistry.domain;

public record Rule(int stepNumber, String rule) {

    public Rule {
        assert stepNumber >= 0;

        assert rule != null && !rule.isEmpty();
    }
}
