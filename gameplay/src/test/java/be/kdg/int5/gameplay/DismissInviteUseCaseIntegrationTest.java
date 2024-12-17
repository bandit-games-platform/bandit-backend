package be.kdg.int5.gameplay;

import be.kdg.int5.gameplay.adapters.out.db.gameInvite.GameInviteJpaEntity;
import be.kdg.int5.gameplay.adapters.out.db.gameInvite.GameInviteJpaRepository;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaEntity;
import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaRepository;
import be.kdg.int5.gameplay.domain.GameInviteId;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.DismissInviteCommand;
import be.kdg.int5.gameplay.port.in.DismissInviteUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class DismissInviteUseCaseIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private DismissInviteUseCase dismissInviteUseCase;
    @Autowired
    private GameInviteJpaRepository gameInviteJpaRepository;
    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;

    @Test
    void dismissInviteShouldFailIfInviteDoesNotExist() {
        // Arrange
        // Act
        boolean result = dismissInviteUseCase.dismissInvite(new DismissInviteCommand(
                new GameInviteId(Variables.NONEXISTENT_INVITE),
                new PlayerId(Variables.PLAYER_ONE_UUID)
        ));
        // Assert
        assertFalse(result);
    }

    @Test
    void dismissInviteShouldFailIfRequestingPlayerIsNotTheInvitedPlayer() {
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
        boolean result = dismissInviteUseCase.dismissInvite(new DismissInviteCommand(
                new GameInviteId(Variables.INVITE_ID),
                new PlayerId(Variables.PLAYER_ONE_UUID)
        ));
        // Assert
        assertFalse(result);
        // Annihilate
        gameInviteJpaRepository.deleteById(Variables.INVITE_ID);
        lobbyJpaRepository.deleteById(Variables.LOBBY_ID);
    }

    @Test
    void dismissInviteShouldSucceedWithExistingInviteAndInvitedPlayerIsTheRequestingPlayer() {
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
        boolean result = dismissInviteUseCase.dismissInvite(new DismissInviteCommand(
                new GameInviteId(Variables.INVITE_ID),
                new PlayerId(Variables.PLAYER_TWO_UUID)
        ));
        // Assert
        assertTrue(result);
        // Annihilate
        gameInviteJpaRepository.deleteById(Variables.INVITE_ID);
        lobbyJpaRepository.deleteById(Variables.LOBBY_ID);
    }
}
