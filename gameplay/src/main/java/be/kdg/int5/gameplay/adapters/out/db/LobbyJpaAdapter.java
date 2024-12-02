package be.kdg.int5.gameplay.adapters.out.db;

import be.kdg.int5.gameplay.domain.*;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import be.kdg.int5.gameplay.port.out.LobbySavePort;
import org.springframework.stereotype.Repository;

@Repository
public class LobbyJpaAdapter implements LobbyLoadPort, LobbySavePort {
    private final LobbyJpaRepository repository;

    public LobbyJpaAdapter(LobbyJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lobby load(LobbyId lobbyId) {
        return repository.findByIdWithInvites(lobbyId.uuid())
                .map(this::mapLobbyEntityToDomain)
                .orElse(null);
    }

    @Override
    public Lobby loadWithoutInvites(LobbyId lobbyId) {
        return repository.findById(lobbyId.uuid())
                .map(this::mapLobbyEntityToDomain)
                .orElse(null);
    }

    public Lobby mapLobbyEntityToDomain(LobbyJpaEntity jpa) {
        return new Lobby(
                new LobbyId(jpa.getId()),
                new GameId(jpa.getGameId()),
                jpa.getMaxPlayers(),
                new PlayerId(jpa.getOwnerId()),
                jpa.getCurrentPlayerCount(),
                jpa.isClosed(),
                jpa.getGameInvites().stream().map(this::mapGameInviteEntityToDomain).toList()
        );
    }

    public GameInvite mapGameInviteEntityToDomain(GameInviteJpaEntity jpa) {
        return new GameInvite(
                new PlayerId(jpa.getId().getInvitedPlayerId()),
                jpa.isAccepted()
        );
    }

    @Override
    public void save(Lobby lobby) {
        repository.save(mapLobbyDomainToEntity(lobby));
    }

    public LobbyJpaEntity mapLobbyDomainToEntity(Lobby lobby) {
        return new LobbyJpaEntity(
                lobby.getId().uuid(),
                lobby.getGameId().uuid(),
                lobby.getOwnerId().uuid(),
                lobby.getMaxPlayers(),
                lobby.getCurrentPlayerCount(),
                lobby.isClosed(),
                lobby.getGameInviteList()
                        .stream()
                        .map(invite -> mapGameInviteDomainToEntity(invite, lobby.getId()))
                        .toList()
        );
    }

    public GameInviteJpaEntity mapGameInviteDomainToEntity(GameInvite invite, LobbyId lobbyId) {
        return new GameInviteJpaEntity(
                new GameInviteJpaEntityId(lobbyId.uuid(), invite.getInvited().uuid()),
                invite.isAccepted()
        );
    }
}
