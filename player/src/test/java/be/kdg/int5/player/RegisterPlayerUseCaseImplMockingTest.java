package be.kdg.int5.player;

import be.kdg.int5.player.core.RegisterPlayerUseCaseImpl;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.RegisterPlayerCommand;
import be.kdg.int5.player.port.out.CreatePlayerPort;
import be.kdg.int5.player.port.out.LoadPlayerPort;
import be.kdg.int5.player.port.out.UpdatePlayerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegisterPlayerUseCaseImplMockingTest {
    private RegisterPlayerUseCaseImpl registerPlayerUseCase;

    private CreatePlayerPort createPlayerPort;
    private LoadPlayerPort loadPlayerPort;
    private UpdatePlayerPort updatePlayerPort;

    @BeforeEach
    void setup() {
        createPlayerPort = mock(CreatePlayerPort.class);
        loadPlayerPort = mock(LoadPlayerPort.class);
        updatePlayerPort = mock(UpdatePlayerPort.class);

        when(loadPlayerPort.loadPlayerById(Variables.PLAYER_ONE_UUID))
                .thenReturn(null);
        when(loadPlayerPort.loadPlayerById(Variables.PLAYER_TWO_UUID))
                .thenReturn(new Player(new PlayerId(Variables.PLAYER_TWO_UUID), Variables.PLAYER_TWO_USERNAME));

        registerPlayerUseCase = new RegisterPlayerUseCaseImpl(createPlayerPort, loadPlayerPort, updatePlayerPort);
    }

    @Test
    void NewPlayerShouldBeSavedWhenNewPlayerRegisters() {
        // Act
        registerPlayerUseCase.registerPlayer(new RegisterPlayerCommand(Variables.PLAYER_ONE_UUID, Variables.PLAYER_ONE_USERNAME));

        // Assert
        verify(loadPlayerPort, times(1)).loadPlayerById(Variables.PLAYER_ONE_UUID);

        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(createPlayerPort, times(1)).createPlayer(playerArgumentCaptor.capture());
        verify(updatePlayerPort, never()).updatePlayerDisplayName(playerArgumentCaptor.capture());

        assertEquals(playerArgumentCaptor.getValue().getId().uuid(), Variables.PLAYER_ONE_UUID);
        assertEquals(playerArgumentCaptor.getValue().getDisplayName(), Variables.PLAYER_ONE_USERNAME);
    }

    @Test
    void PlayerShouldBeUpdatedWhenExistingPlayerRegisters() {
        // Act
        registerPlayerUseCase.registerPlayer(new RegisterPlayerCommand(Variables.PLAYER_TWO_UUID, Variables.PLAYER_TWO_NEW_USERNAME));

        // Assert
        verify(loadPlayerPort, times(1)).loadPlayerById(Variables.PLAYER_TWO_UUID);

        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(createPlayerPort, never()).createPlayer(playerArgumentCaptor.capture());
        verify(updatePlayerPort, times(1)).updatePlayerDisplayName(playerArgumentCaptor.capture());

        assertEquals(playerArgumentCaptor.getValue().getId().uuid(), Variables.PLAYER_TWO_UUID);
        assertEquals(playerArgumentCaptor.getValue().getDisplayName(), Variables.PLAYER_TWO_NEW_USERNAME);
    }
}
