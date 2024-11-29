package be.kdg.int5.player;

import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.RegisterPlayerUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterPlayerUseCaseImplIntegrationTest extends AbstractDatabaseTest {
    private static final String authorizationId = "4b0f0827-3951-4839-85de-7e4335069389";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerJpaRepository playerJpaRepository;

    @Test
    void newPlayerShouldBeSavedWhenNewPlayerRegisters() throws Exception {
        // Arrange
        when(playerJpaRepository.findById(Variables.PLAYER_ONE_UUID)).thenReturn(
                Optional.empty()
        );

        UUID urlId = UUID.nameUUIDFromBytes((authorizationId + "-" + Variables.PLAYER_ONE_UUID + "-" + Variables.PLAYER_ONE_USERNAME).getBytes());

        // Act
        ResultActions result = mockMvc
                .perform(post("/registration/" + urlId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": \"%s\", \"username\":  \"%s\"}"
                        .formatted(Variables.PLAYER_ONE_UUID, Variables.PLAYER_ONE_USERNAME))
                );

        result.andExpect(status().isOk());

        ArgumentCaptor<PlayerJpaEntity> playerArgumentCaptor = ArgumentCaptor.forClass(PlayerJpaEntity.class);
        verify(playerJpaRepository, times(1)).save(playerArgumentCaptor.capture());

        assertEquals(playerArgumentCaptor.getValue().getId(), Variables.PLAYER_ONE_UUID);
        assertEquals(playerArgumentCaptor.getValue().getDisplayName(), Variables.PLAYER_ONE_USERNAME);
    }

    @Test
    void playerShouldBeUpdatedWhenNewPlayerRegisters() throws Exception {
        // Arrange
        when(playerJpaRepository.findById(Variables.PLAYER_TWO_UUID)).thenReturn(
                Optional.of(new PlayerJpaEntity(Variables.PLAYER_TWO_UUID, LocalDateTime.now(), Variables.PLAYER_TWO_USERNAME))
        );

        UUID urlId = UUID.nameUUIDFromBytes((authorizationId + "-" + Variables.PLAYER_TWO_UUID + "-" + Variables.PLAYER_TWO_NEW_USERNAME).getBytes());

        // Act
        ResultActions result = mockMvc
                .perform(post("/registration/" + urlId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"%s\", \"username\":  \"%s\"}"
                                .formatted(Variables.PLAYER_TWO_UUID, Variables.PLAYER_TWO_NEW_USERNAME))
                );

        result.andExpect(status().isOk());

        ArgumentCaptor<PlayerJpaEntity> playerArgumentCaptor = ArgumentCaptor.forClass(PlayerJpaEntity.class);
        verify(playerJpaRepository, times(1)).save(playerArgumentCaptor.capture());

        assertEquals(playerArgumentCaptor.getValue().getId(), Variables.PLAYER_TWO_UUID);
        assertEquals(playerArgumentCaptor.getValue().getDisplayName(), Variables.PLAYER_TWO_NEW_USERNAME);
    }
}
