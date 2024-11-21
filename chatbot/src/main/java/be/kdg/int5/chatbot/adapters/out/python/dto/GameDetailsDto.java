package be.kdg.int5.chatbot.adapters.out.python.dto;

import java.util.List;
import java.util.UUID;

public class GameDetailsDto {
    private UUID id;
    private String title;
    private String description;
    private List<GameRuleDto> rules;

    public GameDetailsDto() {
    }

    public GameDetailsDto(UUID id, String title, String description, List<GameRuleDto> rules) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rules = rules;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GameRuleDto> getRules() {
        return rules;
    }

    public void setRules(List<GameRuleDto> rules) {
        this.rules = rules;
    }
}
