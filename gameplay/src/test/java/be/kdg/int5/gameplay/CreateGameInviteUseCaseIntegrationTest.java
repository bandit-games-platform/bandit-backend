package be.kdg.int5.gameplay;

import be.kdg.int5.gameplay.adapters.out.db.gameInvite.GameInviteJpaRepository;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaRepository;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.CreateGameInviteCommand;
import be.kdg.int5.gameplay.port.in.CreateGameInviteUseCase;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameInviteUseCaseIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private CreateGameInviteUseCase createGameInviteUseCase;
    @Autowired
    private GameInviteJpaRepository gameInviteJpaRepository;
    @Autowired
    private CreateLobbyUseCase createLobbyUseCase;
    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;

    @Test
    void createGameInviteWithNonExistentLobbyShouldFail() {
        // Arrange
        // Act
        boolean result = createGameInviteUseCase.createInvite(new CreateGameInviteCommand(
                new LobbyId(Variables.NONEXISTENT_LOBBY),
                new PlayerId(Variables.PLAYER_ONE_UUID),
                new PlayerId(Variables.PLAYER_TWO_UUID)
        ));
        // Assert
        assertFalse(result);
    }

    @Test
    void createGameInviteWithExistingLobbyButNotOwnerAsInviterShouldFail() {
        // Arrange
        LobbyId createdLobbyId = createLobbyUseCase.create(new CreateLobbyCommand(
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2
        ));
        // Act
        boolean result = createGameInviteUseCase.createInvite(new CreateGameInviteCommand(
                createdLobbyId,
                new PlayerId(Variables.PLAYER_ONE_UUID),
                new PlayerId(Variables.PLAYER_TWO_UUID)
        ));
        // Assert
        assertFalse(result);
        // Annihilate
        lobbyJpaRepository.deleteById(createdLobbyId.uuid());
    }

    @Test
    void createGameInviteWithExistingLobbyAndOwnerAsInviterShouldSucceedAndSaveToDB() {
        // Arrange
        LobbyId createdLobbyId = createLobbyUseCase.create(new CreateLobbyCommand(
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2
        ));
        int invitesBefore = gameInviteJpaRepository.findAll().size();
        // Act
        boolean result = createGameInviteUseCase.createInvite(new CreateGameInviteCommand(
                createdLobbyId,
                new PlayerId(Variables.PLAYER_TWO_UUID),
                new PlayerId(Variables.PLAYER_ONE_UUID)
        ));
        // Assert
        assertTrue(result);
        assertEquals(invitesBefore+1, gameInviteJpaRepository.findAll().size());
        // Annihilate
        gameInviteJpaRepository.deleteAll();
        lobbyJpaRepository.deleteById(createdLobbyId.uuid());
    }
}
