package be.kdg.int5.gameplay.adapters.out.db.lobby;

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
                jpa.isClosed()
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
                lobby.isClosed()
        );
    }
}
