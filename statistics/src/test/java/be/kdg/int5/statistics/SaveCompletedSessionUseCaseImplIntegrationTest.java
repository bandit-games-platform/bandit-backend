package be.kdg.int5.statistics;

import be.kdg.int5.statistics.adapters.out.db.playerGameStats.*;
import be.kdg.int5.statistics.domain.GameEndState;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionCommand;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class SaveCompletedSessionUseCaseImplIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private SaveCompletedSessionUseCase saveCompletedSessionUseCase;
    @Autowired
    private PlayerGameStatsJpaRepository playerGameStatsJpaRepository;
    @Autowired
    private AchievementProgressJpaRepository achievementProgressJpaRepository;

    @Test
    void whenCompletedSessionWithBasicDetailsForExistingPlayerStatsSubmittedResultsInSuccess() {
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
        saveCompletedSessionUseCase.saveCompletedGameSession(
                new SaveCompletedSessionCommand(
                        new PlayerId(Variables.PLAYER_ONE_UUID),
                        new GameId(Variables.GAME_ID),
                        Variables.NEW_SESSION_START_TIME,
                        Variables.NEW_SESSION_END_TIME,
                        GameEndState.DRAW,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        // Assert
        PlayerGameStatsJpaEntity playerGameStatsJpaEntityUpdated = playerGameStatsJpaRepository.findByIdWithAllRelationships(
                new PlayerGameStatsJpaId(Variables.PLAYER_ONE_UUID, Variables.GAME_ID)
        );

        assertNotNull(playerGameStatsJpaEntityUpdated);
        assertEquals(playerGameStatsJpaEntity.getCompletedSessions().size(), 1);
        assertEquals(playerGameStatsJpaEntityUpdated.getCompletedSessions().size(), 2);
    }

    @Test
    void whenCompletedSessionWithBasicDetailsForNewPlayerStatsSubmittedResultsInSuccess() {
        // Arrange

        // Act
        saveCompletedSessionUseCase.saveCompletedGameSession(
                new SaveCompletedSessionCommand(
                        new PlayerId(Variables.PLAYER_TWO_UUID),
                        new GameId(Variables.GAME_ID),
                        Variables.NEW_SESSION_START_TIME,
                        Variables.NEW_SESSION_END_TIME,
                        GameEndState.DRAW,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        // Assert
        PlayerGameStatsJpaEntity playerGameStatsJpaEntityUpdated = playerGameStatsJpaRepository.findByIdWithAllRelationships(
                new PlayerGameStatsJpaId(Variables.PLAYER_TWO_UUID, Variables.GAME_ID)
        );

        assertNotNull(playerGameStatsJpaEntityUpdated);
        assertEquals(playerGameStatsJpaEntityUpdated.getCompletedSessions().size(), 1);
    }

}
