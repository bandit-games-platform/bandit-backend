package be.kdg.int5.gameplay;

import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaEntity;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaRepository;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import be.kdg.int5.gameplay.port.in.PatchLobbyCommand;
import be.kdg.int5.gameplay.port.in.PatchLobbyUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PatchLobbyUseCaseIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private PatchLobbyUseCase patchLobbyUseCase;
    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;
    @Autowired
    private CreateLobbyUseCase createLobbyUseCase;

    @Test
    void patchLobbyShouldUpdateLobbyDetailsAndReturnTrue() {
        // Arrange
        LobbyId lobbyId = createLobbyUseCase.create(new CreateLobbyCommand(
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2
        ));

        int playerCountBefore = lobbyJpaRepository.findById(lobbyId.uuid()).orElseThrow().getCurrentPlayerCount();
        // Act
        boolean result = patchLobbyUseCase.patch(new PatchLobbyCommand(
                lobbyId.uuid(),
                Variables.PLAYER_TWO_UUID,
                null,
                true
        ));
        // Assert
        assertTrue(result);

        Optional<LobbyJpaEntity> lobbyJpa = lobbyJpaRepository.findById(lobbyId.uuid());
        assertTrue(lobbyJpa.isPresent());

        assertEquals(lobbyId.uuid(), lobbyJpa.get().getId());
        assertEquals(Variables.PLAYER_TWO_UUID, lobbyJpa.get().getOwnerId());
        assertEquals(playerCountBefore, lobbyJpa.get().getCurrentPlayerCount());
        assertTrue(lobbyJpa.get().isClosed());

        // Annihilate
        lobbyJpaRepository.deleteById(lobbyId.uuid());
    }

    @Test
    void patchNonExistentLobbyShouldReturnFalse() {
        // Arrange
        // Act
        boolean result = patchLobbyUseCase.patch(new PatchLobbyCommand(
                Variables.NONEXISTENT_LOBBY,
                null,
                null,
                true
        ));
        // Assert
        assertFalse(result);
    }
}
