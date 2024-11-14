package be.kdg.int5.gameRegistry.domain;

import java.util.Objects;

public class Achievement {
    private static final int MAX_TITLE_LENGTH = 30;

    private final AchievementId id;
    private String title;
    private int counterTotal;
    private String description;

    public Achievement(AchievementId id, String title, int counterTotal, String description) {
        Objects.requireNonNull(id);

        Objects.requireNonNull(title);
        assert !title.isEmpty() && title.length() < MAX_TITLE_LENGTH;

        assert counterTotal > 0;

        Objects.requireNonNull(description);

        this.id = id;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
    }


    public AchievementId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public void setCounterTotal(int counterTotal) {
        this.counterTotal = counterTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
