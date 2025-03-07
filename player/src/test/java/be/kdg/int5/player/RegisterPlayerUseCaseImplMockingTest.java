package be.kdg.int5.player;

import be.kdg.int5.player.core.RegisterPlayerUseCaseImpl;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.RegisterPlayerCommand;
import be.kdg.int5.player.port.out.PlayerCreatePort;
import be.kdg.int5.player.port.out.PlayerLoadPort;
import be.kdg.int5.player.port.out.PlayerUpdatePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegisterPlayerUseCaseImplMockingTest {
    private RegisterPlayerUseCaseImpl registerPlayerUseCase;

    private PlayerCreatePort playerCreatePort;
    private PlayerLoadPort playerLoadPort;
    private PlayerUpdatePort playerUpdatePort;

    @BeforeEach
    void setup() {
        playerCreatePort = mock(PlayerCreatePort.class);
        playerLoadPort = mock(PlayerLoadPort.class);
        playerUpdatePort = mock(PlayerUpdatePort.class);

        when(playerLoadPort.loadPlayerById(Variables.PLAYER_ONE_UUID))
                .thenReturn(null);
        when(playerLoadPort.loadPlayerById(Variables.PLAYER_TWO_UUID))
                .thenReturn(new Player(new PlayerId(Variables.PLAYER_TWO_UUID), LocalDateTime.now().minusMonths(2), Variables.PLAYER_TWO_USERNAME));

        registerPlayerUseCase = new RegisterPlayerUseCaseImpl(playerCreatePort, playerLoadPort, playerUpdatePort);
    }

    @Test
    void NewPlayerShouldBeSavedWhenNewPlayerRegisters() {
        // Act
        registerPlayerUseCase.registerOrUpdatePlayerAccount(new RegisterPlayerCommand(Variables.PLAYER_ONE_UUID, Variables.PLAYER_ONE_USERNAME));

        // Assert
        verify(playerLoadPort, times(1)).loadPlayerById(Variables.PLAYER_ONE_UUID);

        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerCreatePort, times(1)).createPlayer(playerArgumentCaptor.capture());
        verify(playerUpdatePort, never()).updatePlayerDisplayName(playerArgumentCaptor.capture());

        assertEquals(playerArgumentCaptor.getValue().getId().uuid(), Variables.PLAYER_ONE_UUID);
        assertEquals(playerArgumentCaptor.getValue().getDisplayName(), Variables.PLAYER_ONE_USERNAME);
    }

    @Test
    void PlayerShouldBeUpdatedWhenExistingPlayerRegisters() {
        // Act
        registerPlayerUseCase.registerOrUpdatePlayerAccount(new RegisterPlayerCommand(Variables.PLAYER_TWO_UUID, Variables.PLAYER_TWO_NEW_USERNAME));

        // Assert
        verify(playerLoadPort, times(1)).loadPlayerById(Variables.PLAYER_TWO_UUID);

        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerCreatePort, never()).createPlayer(playerArgumentCaptor.capture());
        verify(playerUpdatePort, times(1)).updatePlayerDisplayName(playerArgumentCaptor.capture());

        assertEquals(playerArgumentCaptor.getValue().getId().uuid(), Variables.PLAYER_TWO_UUID);
        assertEquals(playerArgumentCaptor.getValue().getDisplayName(), Variables.PLAYER_TWO_NEW_USERNAME);
    }
}
