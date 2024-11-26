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

    public PlayerGameStats(final PlayerId playerId, final GameId gameId, final AchievementProgress achievementProgress) {
        this.playerId = Objects.requireNonNull(playerId);
        this.gameId = Objects.requireNonNull(gameId);
        completedSessions = new HashSet<>();
        achievementProgressSet = new HashSet<>();

        achievementProgressSet.add(Objects.requireNonNull(achievementProgress));
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

    public void addAchievementProgress(final AchievementProgress achievementProgress) {
        Objects.requireNonNull(achievementProgress, "AchievementProgress cannot be null");
        this.achievementProgressSet.add(achievementProgress);
    }

    public void addAchievementProgressSet(final Set<AchievementProgress> achievementProgressSet) {
        if (achievementProgressSet == null || achievementProgressSet.isEmpty()) {
            throw new IllegalArgumentException("AchievementProgress set cannot be null or empty");
        }
        this.achievementProgressSet.addAll(achievementProgressSet);
    }

    public void addCompletedGameSession(CompletedSession completedSession) {
        Objects.requireNonNull(completedSession);

        for (CompletedSession session: completedSessions) {
            if (session.getStartTime().equals(completedSession.getStartTime()) &&
                    session.getEndTime().equals(completedSession.getEndTime()) &&
                    session.getEndState().equals(completedSession.getEndState())) return;
        }

        this.completedSessions.add(completedSession);
    }

    public void addCompletedSessions(Set<CompletedSession> completedSessions) {
        for (CompletedSession session : completedSessions) {
            addCompletedGameSession(session);
        }
    }
}
