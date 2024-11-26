package be.kdg.int5.gameRegistry.domain;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class Achievement {
    private static final int MAX_TITLE_LENGTH = 30;

    private final AchievementId id;
    private String title;
    private int counterTotal;
    private String description;

    public Achievement(String title, int counterTotal, String description) {
        this(new AchievementId(UUID.randomUUID()), title, counterTotal, description);
    }

    public Achievement(AchievementId id, String title, int counterTotal, String description) {
        if (title.isEmpty() || title.length() > MAX_TITLE_LENGTH) throw new IllegalArgumentException();

        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.counterTotal = counterTotal <= 0 ? 1 : counterTotal;
        this.description = Objects.requireNonNull(description);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    public static AchievementId generateUniqueIdFromGameIdAndUniqueNumber(GameId gameId, int uniqueNumber) {
        return new AchievementId(UUID.nameUUIDFromBytes((gameId.toString()+":"+uniqueNumber).getBytes(StandardCharsets.UTF_8)));
    }
}
