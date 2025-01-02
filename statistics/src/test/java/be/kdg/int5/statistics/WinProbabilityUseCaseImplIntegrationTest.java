package be.kdg.int5.statistics;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityFeaturesDto;
import be.kdg.int5.statistics.core.WinProbabilityUseCaseImpl;
import be.kdg.int5.statistics.domain.*;
import be.kdg.int5.statistics.port.in.WinProbabilityCommand;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import be.kdg.int5.statistics.port.out.PredictWinProbabilityPort;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WinProbabilityUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private PredictWinProbabilityPort pythonClientAdapter;

    @Autowired
    private WinProbabilityUseCaseImpl winProbabilityUseCase;

    @MockBean
    private CompletedSessionLoadPort completedSessionLoadPort;

    @MockBean
    private RestTemplate restTemplate;

    private UUID gameId;
    private UUID playerOneId;
    private UUID playerTwoId;
    private WinProbabilityFeaturesDto modelFeaturesDto;
    private WinProbabilityCommand command;

    @BeforeEach
    void setUp() {
        gameId = Variables.GAME_ID;
        playerOneId = Variables.PLAYER_ONE_UUID;
        playerTwoId = Variables.PLAYER_TWO_UUID;

        command = new WinProbabilityCommand(
                new GameId(gameId),
                new PlayerId(playerOneId),
                new PlayerId(playerTwoId)
        );

        modelFeaturesDto = new WinProbabilityFeaturesDto(
                playerOneId,
                playerTwoId,
                2.5f,
                0.15f,
                10.0f,
                -1.0f,
                300.0f,
                -2.0f,
                5.0f,
                -8.0f,
                0.2f
        );
    }

    @Test
    void whenInvalidPythonUrlPythonServiceExceptionShouldBeThrown() {
        // Arrange
        String expectedErrorMessage = "An error occurred while calling the Python service.";

        when(restTemplate.postForObject(eq("http://invalid-python-url/predict/win-probability"),
                any(HttpEntity.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"));

        // Act & Assert
        PythonServiceException exception = assertThrows(PythonServiceException.class, () -> {
            pythonClientAdapter.getPredictions(modelFeaturesDto);
        });
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void whenChosenPlayerOneHasNoSessionsForChosenGameExceptionShouldBeThrown() {
        // Arrange
        CompletedSession completedSession = new CompletedSession(
                new SessionId(UUID.randomUUID()),
                LocalDateTime.of(2024,11,24,19,0),
                LocalDateTime.of(2024,11,24,23,0),
                GameEndState.WIN,
                40,
                66.0,
                null, null, null,
                "Mr_White",
                true
        );

        when(completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                new GameId(gameId),
                new PlayerId(playerOneId)))
                .thenReturn(null);
        when(completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                new GameId(gameId),
                new PlayerId(playerTwoId)))
                .thenReturn(Collections.singletonList(completedSession));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            winProbabilityUseCase.predictWinProbability(command);
        });
        assertEquals("Player 1 has no completed sessions for the selected game.", exception.getMessage());
    }

    @Test
    void whenChosenPlayerTwoHasNoSessionsForChosenGameExceptionShouldBeThrown() {
        // Arrange
        CompletedSession completedSession = new CompletedSession(
                new SessionId(UUID.randomUUID()),
                LocalDateTime.of(2024,11,24,19,0),
                LocalDateTime.of(2024,11,24,23,0),
                GameEndState.WIN,
                40,
                66.0,
                null, null, null,
                "Mr_White",
                true
        );

        when(completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                new GameId(gameId),
                new PlayerId(playerOneId)))
                .thenReturn(Collections.singletonList(completedSession));
        when(completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                new GameId(gameId),
                new PlayerId(playerTwoId)))
                .thenReturn(null);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            winProbabilityUseCase.predictWinProbability(command);
        });
        assertEquals("Player 2 has no completed sessions for the selected game.", exception.getMessage());
    }
}
