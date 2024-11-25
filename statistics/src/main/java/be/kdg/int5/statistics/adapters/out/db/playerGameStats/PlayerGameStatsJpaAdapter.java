package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.*;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PlayerGameStatsJpaAdapter implements PlayerGameStatisticsLoadPort {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlayerGameStatsJpaAdapter.class);
    private final PlayerGameStatsJpaRepository playerGameStatsJpaRepository;
    private final AchievementProgressJpaRepository achievementProgressJpaRepository;

    public PlayerGameStatsJpaAdapter(
            final PlayerGameStatsJpaRepository playerGameStatsJpaRepository,
            final AchievementProgressJpaRepository achievementProgressJpaRepository) {
        this.playerGameStatsJpaRepository = playerGameStatsJpaRepository;
        this.achievementProgressJpaRepository = achievementProgressJpaRepository;
    }


    @Override
    public PlayerGameStats loadPlayerGameStat(PlayerId playerId, GameId gameId) {
        PlayerGameStatsJpaId playerGameStatsJpaId = new PlayerGameStatsJpaId(playerId.uuid(), gameId.uuid());
        PlayerGameStatsJpaEntity playerGameStatsJpaEntity = playerGameStatsJpaRepository
                .findById(playerGameStatsJpaId)
                .orElseThrow();

        return this.playerGameStatsJpaToDomain(playerGameStatsJpaEntity);
    }

    private PlayerGameStatsJpaEntity playerGameStatsDomainToJpa(final PlayerGameStats playerGameStats){
        PlayerGameStatsJpaId playerGameStatsJpaId = new PlayerGameStatsJpaId(playerGameStats.getPlayerId().uuid(), playerGameStats.getGameId().uuid());
        Set<CompletedSessionJpaEntity> completedSessions = playerGameStats.getCompletedSessions().stream()
                .map(this::completedSessionDomainToJpa)
                .collect(Collectors.toSet());

        return new PlayerGameStatsJpaEntity(playerGameStatsJpaId, completedSessions);
    }

    private PlayerGameStats playerGameStatsJpaToDomain(final PlayerGameStatsJpaEntity playerGameStatsJpaEntity) {
        // Step 1: Map and filter sessions
        Set<CompletedSession> completedSessionsList = playerGameStatsJpaEntity.getCompletedSessions().stream()
                .map(this::completedSessionJpaToDomain)
                .filter(completedSession -> completedSession.getStartTime() != null)
                .collect(Collectors.toSet());

        if (completedSessionsList.isEmpty()) {
            throw new IllegalStateException("No completed sessions available.");
        }

        // Step 2: Find the earliest session
        CompletedSession earliestSession = completedSessionsList.stream()
                .min(Comparator.comparing(CompletedSession::getStartTime))
                .orElseThrow(() -> new IllegalStateException("Unable to find the earliest session."));

        // Step 3: Collect all sessions except the earliest
        Set<CompletedSession> remainingSessions = completedSessionsList.stream()
                .filter(session -> !session.equals(earliestSession))
                .collect(Collectors.toSet());

        // Step 4: Return new PlayerGameStats with the earliest session and other sessions
        PlayerGameStats playerGameStats = new PlayerGameStats(
                new PlayerId(playerGameStatsJpaEntity.getId().getPlayerId()),
                new GameId(playerGameStatsJpaEntity.getId().getGameId()),
                earliestSession // Constructor accepts a single `CompletedSession`
        );

        // Add remaining sessions to `playerGameStats`
        playerGameStats.getCompletedSessions().addAll(remainingSessions);

        Set<AchievementProgress> achievementProgressSet = playerGameStatsJpaEntity.getAchievementProgressJpaEntities()
                .stream()
                .map(this::achievementProgressJpaToDomain)
                .collect(Collectors.toSet());

        playerGameStats.addAchievementProgressSet(achievementProgressSet);

        return playerGameStats;
    }

    private CompletedSessionJpaEntity completedSessionDomainToJpa(final CompletedSession completedSession){
        return new CompletedSessionJpaEntity(
            completedSession.getSessionId().uuid(),
                completedSession.getStartTime(),
                completedSession.getEndTime(),
                completedSession.getEndState(),
                completedSession.getTurnsTaken(),
                completedSession.getAvgSecondsPerTurn(),
                completedSession.getPlayerScore(),
                completedSession.getOpponentScore(),
                completedSession.getClicks(),
                completedSession.getCharacter(),
                completedSession.getWasFirstToGo()
        );
    }

    private CompletedSession completedSessionJpaToDomain(final CompletedSessionJpaEntity completedSessionJpaEntity){
        return new CompletedSession(
                new SessionId(completedSessionJpaEntity.getSessionId()),
                completedSessionJpaEntity.getStartTime(),
                completedSessionJpaEntity.getEndTime(),
                completedSessionJpaEntity.getEndState(),
                completedSessionJpaEntity.getTurnsTaken(),
                completedSessionJpaEntity.getAvgSecondsPerTurn(),
                completedSessionJpaEntity.getPlayerScore(),
                completedSessionJpaEntity.getOpponentScore(),
                completedSessionJpaEntity.getClicks(),
                completedSessionJpaEntity.getCharacter(),
                completedSessionJpaEntity.isWasFirstToGo()
        );
    }

    private AchievementProgress achievementProgressJpaToDomain(final AchievementProgressJpaEntity achievementProgressJpaEntity){
        return new AchievementProgress(
                new AchievementId(achievementProgressJpaEntity.getAchievementId()),
                        achievementProgressJpaEntity.getCounterTotal()
        );
    }

    private AchievementProgressJpaEntity achievementProgressDomainToJpa(final AchievementProgress achievementProgress, PlayerId playerId){

        final AchievementProgressJpaEntity achievementProgressJpaEntity = achievementProgressJpaRepository.findAchievementProgressJpaEntitiesByAchievementIdAndPlayerGameStatsJpaEntity_IdPlayerId(
                achievementProgress.getAchievementId().uuid(),
                playerId.uuid()
        ).orElseThrow();

        return new AchievementProgressJpaEntity(
                achievementProgressJpaEntity.getAchievementProgressId(),
                achievementProgress.getAchievementId().uuid(),
                achievementProgressJpaEntity.getPlayerGameStatsJpaEntity(),
                achievementProgress.getCounterValue()
                );
    }
}
