package be.kdg.int5.statistics;

import be.kdg.int5.statistics.adapters.in.dto.WinProbabilityRequestDto;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.WinProbabilityCommand;
import be.kdg.int5.statistics.port.in.WinProbabilityUseCase;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WinPredictionControllerIntegrationTest extends AbstractDatabaseTest {

    @MockBean
    private WinProbabilityUseCase winProbabilityUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID gameId;
    private UUID adminId;
    private UUID notAnAdminId;
    private UUID playerOneId;
    private UUID playerTwoId;
    private float winProbabilityPlayerOne;
    private float winProbabilityPlayerTwo;
    private WinPrediction winPrediction;
    private WinProbabilityCommand command;
    private WinProbabilityRequestDto requestDto;

    @BeforeEach
    void setUp() {
        adminId = Variables.ADMIN_ID;
        gameId = Variables.GAME_ID;
        playerOneId = Variables.PLAYER_ONE_UUID;
        playerTwoId = Variables.PLAYER_TWO_UUID;
        winProbabilityPlayerOne = Variables.WIN_PROBABILITY_PLAYER_ONE;
        winProbabilityPlayerTwo = Variables.WIN_PROBABILITY_PLAYER_TWO;

        command = new WinProbabilityCommand(
                new GameId(gameId),
                new PlayerId(playerOneId),
                new PlayerId(playerTwoId)
        );

        winPrediction = new WinPrediction(
                playerOneId,
                winProbabilityPlayerOne,
                playerTwoId,
                winProbabilityPlayerTwo
        );

        requestDto = new WinProbabilityRequestDto(
                playerOneId,
                playerTwoId
        );
    }


    @Test
    void whenAdminPredictsWinProbabilityShouldSucceed() throws Exception {

        // Arrange
        when(winProbabilityUseCase.predictWinProbability(command))
                .thenReturn(winPrediction);

        // Act
        final ResultActions result = mockMvc.perform(post("/statistics/games/" + gameId + "/win-probability/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(adminId)))
                        .authorities(new SimpleGrantedAuthority("admin"))));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOneId").value(playerOneId.toString()))
                .andExpect(jsonPath("$.winProbabilityPlayerOne").value(winProbabilityPlayerOne))
                .andExpect(jsonPath("$.playerTwoId").value(playerTwoId.toString()))
                .andExpect(jsonPath("$.winProbabilityPlayerTwo").value(winProbabilityPlayerTwo));

        ArgumentCaptor<WinProbabilityCommand> captor = ArgumentCaptor.forClass(WinProbabilityCommand.class);
        verify(winProbabilityUseCase).predictWinProbability(captor.capture());
        assertEquals(command, captor.getValue());
    }

    @Test
    void whenNotAdminPredictsWinProbabilityShouldFail() throws Exception {

        // Arrange
        when(winProbabilityUseCase.predictWinProbability(command))
                .thenReturn(winPrediction);

        // Act
        final ResultActions result = mockMvc.perform(post("/statistics/games/" + gameId + "/win-probability/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(notAnAdminId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isForbidden());
        verify(winProbabilityUseCase, times(0)).predictWinProbability(command);
    }
}