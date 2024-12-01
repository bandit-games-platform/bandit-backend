package be.kdg.int5.chatbot.domain;

import java.util.Objects;
import java.util.Set;

public class GameDetails {
    private final GameId id;
    private String title;
    private String description;
    private Set<GameRule> rules;

    public GameDetails(GameId id, String title, String description, Set<GameRule> rules) {
        this.id = Objects.requireNonNull(id);
        this.title = title;
        this.description = description;
        this.rules = rules;
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
}
