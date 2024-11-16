package be.kdg.int5.gameRegistry.adapters.in.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LoadRuleDto {

    @Min(value = 1 , message = "stepNumber must be greater than 0")
    private int stepNumber;
    @NotNull
    private String rule;

    public LoadRuleDto() {
    }

    public LoadRuleDto(int stepNumber, String rule) {
        this.stepNumber = stepNumber;
        this.rule = rule;
    }

    @Min(value = 1, message = "stepNumber must be greater than 1")
    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(@Min(value = 1, message = "stepNumber must be greater than 1") int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public @NotNull String getRule() {
        return rule;
    }

    public void setRule(@NotNull String rule) {
        this.rule = rule;
    }
}
