package be.kdg.int5.statistics.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PlayerGameStats {
    private final PlayerId playerId;
    private final GameId gameId;
    private Set<CompletedSession> completedSessions;
    private Set<AchievementProgress> achievementProgressSet;

    public PlayerGameStats(final PlayerId playerId, final GameId gameId, final CompletedSession completedSession) {
        this.playerId = Objects.requireNonNull(playerId);
        this.gameId = Objects.requireNonNull(gameId);
        completedSessions = new HashSet<>();
        achievementProgressSet = new HashSet<>();

        completedSessions.add(Objects.requireNonNull(completedSession));
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public Set<CompletedSession> getCompletedSessions() {
        return completedSessions;
    }

    public Set<AchievementProgress> getAchievementProgressSet() {
        return achievementProgressSet;
    }
}
