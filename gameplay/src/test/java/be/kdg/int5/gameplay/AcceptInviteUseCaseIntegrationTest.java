package be.kdg.int5.gameplay;

import be.kdg.int5.gameplay.adapters.out.db.gameInvite.GameInviteJpaEntity;
import be.kdg.int5.gameplay.adapters.out.db.gameInvite.GameInviteJpaRepository;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaEntity;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaRepository;
import be.kdg.int5.gameplay.domain.GameInviteId;
import be.kdg.int5.gameplay.domain.LobbyJoinInfo;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.AcceptInviteCommand;
import be.kdg.int5.gameplay.port.in.AcceptInviteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptInviteUseCaseIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private AcceptInviteUseCase acceptInviteUseCase;
    @Autowired
    private GameInviteJpaRepository gameInviteJpaRepository;
    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;

    @Test
    void acceptInviteShouldThrowIfInviteDoesNotExist() {
        // Arrange
        // Act
        Executable test = () -> acceptInviteUseCase.acceptInviteAndGetJoinInfo(new AcceptInviteCommand(
                new GameInviteId(Variables.NONEXISTENT_INVITE),
                new PlayerId(Variables.PLAYER_ONE_UUID)
        ));
        // Assert
        assertThrows(AcceptInviteUseCase.NoSuchInviteException.class, test);
    }

    @Test
    void acceptInviteShouldThrowIfInviteExistsButRequestingPlayerIsNotTheInvitedPlayer() {
        // Arrange
        LobbyJpaEntity lobby = lobbyJpaRepository.save(new LobbyJpaEntity(
                Variables.LOBBY_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2,
                1,
                false
        ));
        gameInviteJpaRepository.save(new GameInviteJpaEntity(
                Variables.INVITE_ID,
                Variables.PLAYER_ONE_UUID,
                Variables.PLAYER_TWO_UUID,
                false,
                lobby
        ));
        // Act
        Executable test = () -> acceptInviteUseCase.acceptInviteAndGetJoinInfo(new AcceptInviteCommand(
                new GameInviteId(Variables.INVITE_ID),
                new PlayerId(Variables.PLAYER_ONE_UUID)
        ));
        // Assert
        assertThrows(AcceptInviteUseCase.NoSuchInviteException.class, test);
        // Annihilate
        gameInviteJpaRepository.deleteById(Variables.INVITE_ID);
        lobbyJpaRepository.deleteById(Variables.LOBBY_ID);
    }

    @Test
    void acceptInviteShouldThrowIfInviteHasNullForLobby() {
        // Arrange
        gameInviteJpaRepository.save(new GameInviteJpaEntity(
                Variables.INVITE_ID,
                Variables.PLAYER_ONE_UUID,
                Variables.PLAYER_TWO_UUID,
                false,
                null
        ));
        // Act
        Executable test = () -> acceptInviteUseCase.acceptInviteAndGetJoinInfo(new AcceptInviteCommand(
                new GameInviteId(Variables.INVITE_ID),
                new PlayerId(Variables.PLAYER_TWO_UUID)
        ));
        // Assert
        assertThrows(RuntimeException.class, test);
        // Annihilate
        gameInviteJpaRepository.deleteById(Variables.INVITE_ID);
    }

    @Test
    void acceptInviteShouldThrowIfLobbyIsClosed() {
        // Arrange
        LobbyJpaEntity lobby = lobbyJpaRepository.save(new LobbyJpaEntity(
                Variables.LOBBY_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2,
                1,
                true
        ));
        gameInviteJpaRepository.save(new GameInviteJpaEntity(
                Variables.INVITE_ID,
                Variables.PLAYER_ONE_UUID,
                Variables.PLAYER_TWO_UUID,
                false,
                lobby
        ));
        // Act
        Executable test = () -> acceptInviteUseCase.acceptInviteAndGetJoinInfo(new AcceptInviteCommand(
                new GameInviteId(Variables.INVITE_ID),
                new PlayerId(Variables.PLAYER_TWO_UUID)
        ));
        // Assert
        assertThrows(AcceptInviteUseCase.LobbyClosedException.class, test);
        // Annihilate
        gameInviteJpaRepository.deleteById(Variables.INVITE_ID);
        lobbyJpaRepository.deleteById(Variables.LOBBY_ID);
    }

    @Test
    void acceptInviteShouldSucceedWithExistingInviteAndOpenLobbyAndInvitedPlayer() {
        // Arrange
        LobbyJpaEntity lobby = lobbyJpaRepository.save(new LobbyJpaEntity(
                Variables.LOBBY_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_UUID,
                2,
                1,
                false
        ));
        gameInviteJpaRepository.save(new GameInviteJpaEntity(
                Variables.INVITE_ID,
                Variables.PLAYER_ONE_UUID,
                Variables.PLAYER_TWO_UUID,
                false,
                lobby
        ));
        try {
            // Act
            LobbyJoinInfo joinInfo = acceptInviteUseCase.acceptInviteAndGetJoinInfo(new AcceptInviteCommand(
                    new GameInviteId(Variables.INVITE_ID),
                    new PlayerId(Variables.PLAYER_TWO_UUID)
            ));

            // Assert
            assertNotNull(joinInfo);
            assertEquals(Variables.GAME_ID, joinInfo.gameId().uuid());
            assertEquals(Variables.LOBBY_ID, joinInfo.lobbyId().uuid());
        } catch (AcceptInviteUseCase.NoSuchInviteException | AcceptInviteUseCase.LobbyClosedException e) {
            throw new RuntimeException(e);
        }
        // Annihilate
        gameInviteJpaRepository.deleteById(Variables.INVITE_ID);
        lobbyJpaRepository.deleteById(Variables.LOBBY_ID);
    }
}
