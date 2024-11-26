package be.kdg.int5.statistics;

import be.kdg.int5.statistics.adapters.out.db.playerGameStats.*;
import be.kdg.int5.statistics.domain.*;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionCommand;
import be.kdg.int5.statistics.port.in.UpdateAchievementProgressCommand;
import be.kdg.int5.statistics.port.in.UpdateAchievementProgressUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateAchievementProgressUseCaseImplIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private UpdateAchievementProgressUseCase updateAchievementProgressUseCase;
    @Autowired
    private PlayerGameStatsJpaRepository playerGameStatsJpaRepository;
    @Autowired
    private AchievementProgressJpaRepository achievementProgressJpaRepository;

    @Test
    void whenAchievementProgressForExistingPlayerStatsSubmittedResultsInSuccess() {
        // Arrange
        Set<CompletedSessionJpaEntity> completedSessionJpaEntities = new HashSet<>();
        completedSessionJpaEntities.add(new CompletedSessionJpaEntity(
                Variables.SAMPLE_SESSION_ID,
                Variables.SAMPLE_SESSION_START_TIME,
                Variables.SAMPLE_SESSION_END_TIME,
                GameEndState.WIN,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
        PlayerGameStatsJpaEntity playerGameStatsJpaEntity = playerGameStatsJpaRepository.save(
                new PlayerGameStatsJpaEntity(
                        new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID),
                        completedSessionJpaEntities
                )
        );

        // Act
        updateAchievementProgressUseCase.updateAchievementProgress(new UpdateAchievementProgressCommand(
                new PlayerId(Variables.PLAYER_ONE_UUID),
                new GameId(Variables.GAME_ID),
                new AchievementId(Variables.ACHIEVEMENT_ONE_ID),
                null
        ));

        // Assert
        PlayerGameStatsJpaEntity playerGameStatsJpaEntityUpdated = playerGameStatsJpaRepository.findByIdWithAllRelationships(
                new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID)
        );

        AchievementProgressJpaEntity relevantProgress = playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities()
                .stream().filter(achievementProgressJpaEntity ->
                        achievementProgressJpaEntity.getAchievementId().equals(Variables.ACHIEVEMENT_ONE_ID)
                ).findFirst().orElse(null);

        assertNotNull(playerGameStatsJpaEntityUpdated);
        assertNotNull(relevantProgress);
        assertEquals(1, playerGameStatsJpaEntity.getCompletedSessions().size());
        assertEquals(0, playerGameStatsJpaEntity.getAchievementProgressJpaEntities().size());
        assertEquals(1, playerGameStatsJpaEntityUpdated.getCompletedSessions().size());
        assertEquals(1, playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities().size());
        assertEquals(1, relevantProgress.getCounterTotal());

        // Tear-down
        playerGameStatsJpaRepository.deleteById(new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID));
    }

    @Test
    void whenAchievementProgressForExistingPlayerWithAchievementProgressStatsSubmittedResultsInSuccess() {
        // Arrange
        Set<CompletedSessionJpaEntity> completedSessionJpaEntities = new HashSet<>();
        Set<AchievementProgressJpaEntity> achievementProgressJpaEntities = new HashSet<>();
        completedSessionJpaEntities.add(new CompletedSessionJpaEntity(
                Variables.SAMPLE_SESSION_ID,
                Variables.SAMPLE_SESSION_START_TIME,
                Variables.SAMPLE_SESSION_END_TIME,
                GameEndState.WIN,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
        achievementProgressJpaEntities.add(new AchievementProgressJpaEntity(
                Variables.ACHIEVEMENT_ONE_ID,
                1
        ));
        PlayerGameStatsJpaEntity playerGameStatsJpaEntity =
                new PlayerGameStatsJpaEntity(
                        new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID),
                        completedSessionJpaEntities
                );
        playerGameStatsJpaEntity.setAchievementProgressJpaEntities(achievementProgressJpaEntities);
        playerGameStatsJpaRepository.save(playerGameStatsJpaEntity);

        // Act
        updateAchievementProgressUseCase.updateAchievementProgress(new UpdateAchievementProgressCommand(
                new PlayerId(Variables.PLAYER_ONE_UUID),
                new GameId(Variables.GAME_ID),
                new AchievementId(Variables.ACHIEVEMENT_ONE_ID),
                null
        ));

        // Assert
        PlayerGameStatsJpaEntity playerGameStatsJpaEntityUpdated = playerGameStatsJpaRepository.findByIdWithAllRelationships(
                new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID)
        );

        AchievementProgressJpaEntity relevantProgress = playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities()
                .stream().filter(achievementProgressJpaEntity ->
                        achievementProgressJpaEntity.getAchievementId().equals(Variables.ACHIEVEMENT_ONE_ID)
                ).findFirst().orElse(null);

        assertNotNull(playerGameStatsJpaEntityUpdated);
        assertNotNull(relevantProgress);
        assertEquals(1, playerGameStatsJpaEntity.getCompletedSessions().size());
        assertEquals(1, playerGameStatsJpaEntity.getAchievementProgressJpaEntities().size());
        assertEquals(1, playerGameStatsJpaEntityUpdated.getCompletedSessions().size());
        assertEquals(1, playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities().size());
        assertEquals(2, relevantProgress.getCounterTotal());

        // Tear-down
        playerGameStatsJpaRepository.deleteById(new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID));
    }

    @Test
    void whenAchievementProgressForNewPlayerSubmittedResultsInSuccess() {
        // Arrange

        // Act
        updateAchievementProgressUseCase.updateAchievementProgress(new UpdateAchievementProgressCommand(
                new PlayerId(Variables.PLAYER_ONE_UUID),
                new GameId(Variables.GAME_ID),
                new AchievementId(Variables.ACHIEVEMENT_ONE_ID),
                null
        ));

        // Assert
        PlayerGameStatsJpaEntity playerGameStatsJpaEntityUpdated = playerGameStatsJpaRepository.findByIdWithAllRelationships(
                new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID)
        );

        AchievementProgressJpaEntity relevantProgress = playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities()
                .stream().filter(achievementProgressJpaEntity ->
                        achievementProgressJpaEntity.getAchievementId().equals(Variables.ACHIEVEMENT_ONE_ID)
                ).findFirst().orElse(null);

        assertNotNull(playerGameStatsJpaEntityUpdated);
        assertNotNull(relevantProgress);
        assertEquals(0, playerGameStatsJpaEntityUpdated.getCompletedSessions().size());
        assertEquals(1, playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities().size());
        assertEquals(1, relevantProgress.getCounterTotal());

        // Tear-down
        playerGameStatsJpaRepository.deleteById(new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID));
    }

    @Test
    void whenAchievementProgressForExistingPlayerWithStatsSubmittedResultsInSuccess() {
        // Arrange
        Set<CompletedSessionJpaEntity> completedSessionJpaEntities = new HashSet<>();
        Set<AchievementProgressJpaEntity> achievementProgressJpaEntities = new HashSet<>();
        completedSessionJpaEntities.add(new CompletedSessionJpaEntity(
                Variables.SAMPLE_SESSION_ID,
                Variables.SAMPLE_SESSION_START_TIME,
                Variables.SAMPLE_SESSION_END_TIME,
                GameEndState.WIN,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
        achievementProgressJpaEntities.add(new AchievementProgressJpaEntity(
                Variables.ACHIEVEMENT_ONE_ID,
                1
        ));
        PlayerGameStatsJpaEntity playerGameStatsJpaEntity =
                new PlayerGameStatsJpaEntity(
                        new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID),
                        completedSessionJpaEntities
                );
        playerGameStatsJpaEntity.setAchievementProgressJpaEntities(achievementProgressJpaEntities);
        playerGameStatsJpaRepository.save(playerGameStatsJpaEntity);

        // Act
        updateAchievementProgressUseCase.updateAchievementProgress(new UpdateAchievementProgressCommand(
                new PlayerId(Variables.PLAYER_ONE_UUID),
                new GameId(Variables.GAME_ID),
                new AchievementId(Variables.ACHIEVEMENT_TWO_ID),
                10
        ));

        // Assert
        PlayerGameStatsJpaEntity playerGameStatsJpaEntityUpdated = playerGameStatsJpaRepository.findByIdWithAllRelationships(
                new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID)
        );

        AchievementProgressJpaEntity relevantProgress = playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities()
                .stream().filter(achievementProgressJpaEntity ->
                        achievementProgressJpaEntity.getAchievementId().equals(Variables.ACHIEVEMENT_TWO_ID)
                ).findFirst().orElse(null);

        assertNotNull(playerGameStatsJpaEntityUpdated);
        assertNotNull(relevantProgress);
        assertEquals(1, playerGameStatsJpaEntity.getCompletedSessions().size());
        assertEquals(1, playerGameStatsJpaEntity.getAchievementProgressJpaEntities().size());
        assertEquals(1, playerGameStatsJpaEntityUpdated.getCompletedSessions().size());
        assertEquals(2, playerGameStatsJpaEntityUpdated.getAchievementProgressJpaEntities().size());
        assertEquals(10, relevantProgress.getCounterTotal());

        // Tear-down
        playerGameStatsJpaRepository.deleteById(new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID));
    }

}
