package be.kdg.int5.gameplay;

import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaEntity;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaRepository;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CreateLobbyUseCaseIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private CreateLobbyUseCase createLobbyUseCase;
    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;

    @Test
    void createLobbyShouldSuccessfullySaveLobbyToDB() {
        // Arrange
        // Act
        LobbyId createdLobbyId = createLobbyUseCase.create(new CreateLobbyCommand(
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2
        ));
        // Assert
        Optional<LobbyJpaEntity> lobbyJpa = lobbyJpaRepository.findById(createdLobbyId.uuid());
        assertTrue(lobbyJpa.isPresent());

        assertEquals(createdLobbyId.uuid(), lobbyJpa.get().getId());
        assertEquals(Variables.PLAYER_ONE_UUID, lobbyJpa.get().getOwnerId());
        assertEquals(2, lobbyJpa.get().getMaxPlayers());
        // Annihilate
        lobbyJpaRepository.deleteById(createdLobbyId.uuid());
    }

    @Test
    void createLobbyWithLessThanTwoPlayerCapacityShouldThrow() {
        // Arrange
        // Act
        Executable test = () -> createLobbyUseCase.create(new CreateLobbyCommand(
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                1
        ));
        // Assert
        assertThrows(IllegalArgumentException.class, test);
    }
}
