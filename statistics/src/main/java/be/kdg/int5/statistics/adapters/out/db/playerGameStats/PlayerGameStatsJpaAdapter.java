package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.*;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsUpdatePort;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerGameStatsJpaAdapter implements PlayerGameStatisticsLoadPort, PlayerGameStatisticsUpdatePort {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlayerGameStatsJpaAdapter.class);
    private final PlayerGameStatsJpaRepository playerGameStatsJpaRepository;
    private final AchievementProgressJpaRepository achievementProgressJpaRepository;

    public PlayerGameStatsJpaAdapter(
            final PlayerGameStatsJpaRepository playerGameStatsJpaRepository,
            AchievementProgressJpaRepository achievementProgressJpaRepository) {
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

    @Override
    public PlayerGameStats loadPlayerGameStatsWithAllRelationships(PlayerId playerId, GameId gameId) {
        PlayerGameStatsJpaId playerGameStatsJpaId = new PlayerGameStatsJpaId(playerId.uuid(), gameId.uuid());
        PlayerGameStatsJpaEntity playerGameStatsJpaEntity = playerGameStatsJpaRepository
                .findById(playerGameStatsJpaId)
                .orElseThrow();

        return this.playerGameStatisticsJpaToDomain(playerGameStatsJpaEntity);
    }

    @Override
    public List<PlayerGameStats> loadAllPlayerGameStatsForPlayer(PlayerId playerId) {
        List<PlayerGameStatsJpaEntity> playerGameStatsJpaEntities = playerGameStatsJpaRepository.findAllByPlayerId(
                playerId.uuid()
        );
        if (playerGameStatsJpaEntities == null) return null;
        return playerGameStatsJpaEntities.stream().map(this::playerGameStatisticsJpaToDomain).collect(Collectors.toList());
    }


    @Override
    public void addNewCompletedSession(PlayerGameStats playerGameStats) {
        playerGameStatsJpaRepository.save(playerGameStatsDomainToJpa(playerGameStats));
    }

    @Override
    public void updateAchievementProgress(PlayerGameStats playerGameStats) {
        playerGameStatsJpaRepository.save(playerGameStatsDomainToJpa(playerGameStats));
    }

    private PlayerGameStatsJpaEntity playerGameStatsDomainToJpa(final PlayerGameStats playerGameStats){
        PlayerGameStatsJpaId playerGameStatsJpaId = new PlayerGameStatsJpaId(playerGameStats.getPlayerId().uuid(), playerGameStats.getGameId().uuid());
        Set<CompletedSessionJpaEntity> completedSessions = playerGameStats.getCompletedSessions().stream()
                .map(this::completedSessionDomainToJpa)
                .collect(Collectors.toSet());

        Set<AchievementProgressJpaEntity> achievementProgressSet = playerGameStats.getAchievementProgressSet().stream()
                .map(achievementProgress -> {
                    AchievementProgressJpaEntity achievementProgressJpaEntity = achievementProgressJpaRepository.findAchievementProgressByAchievementIdAndPlayerId(
                            achievementProgress.getAchievementId().uuid(),
                            playerGameStats.getPlayerId().uuid()
                    ).orElse(null);

                    if (achievementProgressJpaEntity != null) {
                        achievementProgressJpaEntity.setCounterTotal(achievementProgress.getCounterValue());
                        return achievementProgressJpaEntity;
                    }

                    return new AchievementProgressJpaEntity(
                            achievementProgress.getAchievementId().uuid(),
                            achievementProgress.getCounterValue());
                })
                .collect(Collectors.toSet());

        PlayerGameStatsJpaEntity playerGameStatsJpaEntity =  new PlayerGameStatsJpaEntity(playerGameStatsJpaId, completedSessions);
        playerGameStatsJpaEntity.setAchievementProgressJpaEntities(achievementProgressSet);
        return playerGameStatsJpaEntity;
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

    private PlayerGameStats playerGameStatisticsJpaToDomain(final PlayerGameStatsJpaEntity playerGameStatsJpaEntity) {
        PlayerGameStats playerGameStats = null;

        if (!playerGameStatsJpaEntity.getAchievementProgressJpaEntities().isEmpty()) {
            Set<AchievementProgress> achievementProgressSet = playerGameStatsJpaEntity.getAchievementProgressJpaEntities()
                    .stream()
                    .map(achievementProgress -> new AchievementProgress(
                            new AchievementId(achievementProgress.getAchievementId()),
                            achievementProgress.getCounterTotal()
                    ))
                    .collect(Collectors.toSet());

            AchievementProgress firstAchievement = achievementProgressSet.stream().findFirst().orElseThrow();
            achievementProgressSet.remove(firstAchievement);

            playerGameStats = new PlayerGameStats(
                    new PlayerId(playerGameStatsJpaEntity.getId().getPlayerId()),
                    new GameId(playerGameStatsJpaEntity.getId().getGameId()),
                    firstAchievement
            );

            if (!achievementProgressSet.isEmpty()) playerGameStats.addAchievementProgressSet(achievementProgressSet);
        }

        if (!playerGameStatsJpaEntity.getCompletedSessions().isEmpty()) {
            Set<CompletedSession> completedSessions = playerGameStatsJpaEntity.getCompletedSessions()
                    .stream()
                    .map(this::completedSessionJpaToDomain)
                    .collect(Collectors.toSet());

            if (playerGameStats == null) {
                CompletedSession firstSession = completedSessions.stream().findFirst().orElseThrow();
                completedSessions.remove(firstSession);

                playerGameStats = new PlayerGameStats(
                        new PlayerId(playerGameStatsJpaEntity.getId().getPlayerId()),
                        new GameId(playerGameStatsJpaEntity.getId().getGameId()),
                        firstSession
                );
            }

            if (!completedSessions.isEmpty()) playerGameStats.addCompletedSessions(completedSessions);
        }

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
        final AchievementProgressJpaEntity achievementProgressJpaEntity = achievementProgressJpaRepository.findAchievementProgressByAchievementIdAndPlayerId(
                achievementProgress.getAchievementId().uuid(),
                playerId.uuid()
        ).orElseThrow();

        return new AchievementProgressJpaEntity(
                achievementProgressJpaEntity.getAchievementProgressId(),
                achievementProgressJpaEntity.getAchievementId(),
                achievementProgressJpaEntity.getPlayerGameStatsJpaEntity(),
                achievementProgress.getCounterValue()
                );
    }
}
