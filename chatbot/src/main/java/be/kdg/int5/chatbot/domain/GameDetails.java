package be.kdg.int5.chatbot.domain;

import java.util.Objects;
import java.util.Set;

public class GameDetails {
    private final GameId id;
    private String title;
    private String description;
    private Set<GameRule> rules;
    private String summary;

    public GameDetails(GameId id, String title, String description, Set<GameRule> rules) {
        this(id, title, description, rules, null);
    }

    public GameDetails(GameId id, String title, String description, Set<GameRule> rules, String summary) {
        this.id = Objects.requireNonNull(id);
        this.title = title;
        this.description = description;
        this.rules = rules;
        this.summary = summary;
    }

    public GameId getId() {
        return id;
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

    public Set<GameRule> getRules() {
        return rules;
    }

    public void setRules(Set<GameRule> rules) {
        this.rules = rules;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean hasSummary() {
        return summary != null;
    }
}
