package be.kdg.int5.statistics;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityFeaturesDto;
import be.kdg.int5.statistics.core.PredictWinProbabilityUseCaseImpl;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityCommand;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import be.kdg.int5.statistics.port.out.PredictWinProbabilityPort;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PredictWinProbabilityUseCaseImplIntegrationTest {

    @Autowired
    private CompletedSessionLoadPort completedSessionLoadPort;

    @Autowired
    private PredictWinProbabilityPort pythonClientAdapter;

    @Autowired
    private PredictWinProbabilityUseCaseImpl predictWinProbabilityUseCase;

    @MockBean
    private RestTemplate restTemplate;

    private UUID gameId;
    private UUID playerOneId;
    private UUID playerTwoId;
    private float winProbabilityPlayerOne;
    private float winProbabilityPlayerTwo;
    private WinPrediction winPrediction;
    private WinProbabilityFeaturesDto modelFeaturesDto;
    private PredictWinProbabilityCommand command;
    @BeforeEach
    void setUp() {
        gameId = Variables.GAME_ID;
        playerOneId = Variables.PLAYER_ONE_UUID;
        playerTwoId = Variables.PLAYER_TWO_UUID;
        winProbabilityPlayerOne = Variables.WIN_PROBABILITY_PLAYER_ONE;
        winProbabilityPlayerTwo = Variables.WIN_PROBABILITY_PLAYER_TWO;

        command = new PredictWinProbabilityCommand(
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

        winPrediction = new WinPrediction(
                playerOneId,
                winProbabilityPlayerOne,
                playerTwoId,
                winProbabilityPlayerTwo
        );
    }

    @Test
    void whenPredictWinProbabilityIsCalledShouldReturnPrediction() {
        // Arrange & Act
        WinPrediction result = pythonClientAdapter.getPredictions(modelFeaturesDto);

        // Assert
        assertNotNull(result);
        assertEquals(winPrediction.getPlayerOneId(), result.getPlayerOneId());
        assertEquals(winPrediction.getPlayerTwoId(), result.getPlayerTwoId());
        assertEquals(winPrediction.getWinProbabilityPlayerOne(), result.getWinProbabilityPlayerOne());
        assertEquals(winPrediction.getWinProbabilityPlayerTwo(), result.getWinProbabilityPlayerTwo());
    }

    @Test
    void whenInvalidPythonUrlIsPythonServiceExceptionShouldBeThrown() {

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
}