package be.kdg.int5.gameRegistry.adapters.in.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LoadRuleDto {
    private int stepNumber;
    private String rule;

    public LoadRuleDto() {
    }

    public LoadRuleDto(int stepNumber, String rule) {
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
