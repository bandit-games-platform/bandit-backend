package be.kdg.int5.gameplay.adapters.out.db.gameInvite;

import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaEntity;
import be.kdg.int5.gameplay.domain.*;
import be.kdg.int5.gameplay.port.out.GameInviteLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameInviteJpaAdapter implements GameInviteLoadPort {
    private final GameInviteJpaRepository repository;

    public GameInviteJpaAdapter(GameInviteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public GameInvite load(GameInviteId id) {
        return repository.findByIdWithLobby(id.uuid()).map(this::mapGameInviteEntityToDomain).orElse(null);
    }

    @Override
    public List<GameInvite> loadAllPendingByInvitedPlayerId(PlayerId invited) {
        return repository.findAllByInvitedAndAcceptedIsFalseWithLobby(invited.uuid())
                .stream()
                .map(this::mapGameInviteEntityToDomain)
                .toList();
    }

    private GameInvite mapGameInviteEntityToDomain(GameInviteJpaEntity jpa) {
        return new GameInvite(
                new GameInviteId(jpa.getId()),
                new PlayerId(jpa.getInviter()),
                new PlayerId(jpa.getInvited()),
                mapLobbyEntityToDomain(jpa.getLobby()),
                jpa.isAccepted()
        );
    }

    private Lobby mapLobbyEntityToDomain(LobbyJpaEntity jpa) {
        return new Lobby(
                new LobbyId(jpa.getId()),
                new GameId(jpa.getGameId()),
                jpa.getMaxPlayers(),
                new PlayerId(jpa.getOwnerId()),
                jpa.getCurrentPlayerCount(),
                jpa.isClosed()
        );
    }
}
