package be.kdg.int5.chatbot.adapters.out.python.dto;

public class GameRuleDto {
    private int stepNumber;
    private String rule;

    public GameRuleDto() {
    }

    public GameRuleDto(int stepNumber, String rule) {
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
