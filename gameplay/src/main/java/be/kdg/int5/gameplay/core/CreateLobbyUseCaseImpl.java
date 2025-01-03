package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import be.kdg.int5.gameplay.port.out.LobbyDeletePort;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import be.kdg.int5.gameplay.port.out.LobbySavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateLobbyUseCaseImpl implements CreateLobbyUseCase {
    private final Logger logger = LoggerFactory.getLogger(CreateLobbyUseCaseImpl.class);

    private final LobbyLoadPort lobbyLoadPort;
    private final LobbySavePort lobbySavePort;
    private final LobbyDeletePort lobbyDeletePort;

    public CreateLobbyUseCaseImpl(LobbyLoadPort lobbyLoadPort, LobbySavePort lobbySavePort, LobbyDeletePort lobbyDeletePort) {
        this.lobbyLoadPort = lobbyLoadPort;
        this.lobbySavePort = lobbySavePort;
        this.lobbyDeletePort = lobbyDeletePort;
    }

    @Override
    public LobbyId create(CreateLobbyCommand command) {
        LobbyId newLobbyId = new LobbyId(UUID.randomUUID());

        if (lobbyLoadPort.load(newLobbyId) != null) {
            logger.error("gameplay:create-lobby LobbyId COLLISION: '{}'", newLobbyId);
            return null;
        }

        // Enforce one active lobby per owner per game
        Lobby previousLobby = lobbyLoadPort.loadByOwnerIdAndGameId(
                new PlayerId(command.ownerId()),
                new GameId(command.gameId())
        );
        if (previousLobby != null) {
            lobbyDeletePort.deleteById(previousLobby.getId());
            logger.warn("gameplay:create-lobby Deleted previously existing lobby for owner '{}' and game '{}' with id: '{}'",
                    command.ownerId(),
                    command.gameId(),
                    previousLobby.getId()
            );
        }

        Lobby newLobby = new Lobby(
                newLobbyId,
                new GameId(command.gameId()),
                command.maxPlayers(),
                new PlayerId(command.ownerId())
        );

        lobbySavePort.save(newLobby);
        logger.info("gameplay:create-lobby Created new lobby '{}' for owner '{}' and game '{}'",
                newLobbyId,
                command.ownerId(),
                command.gameId()
        );
        return newLobbyId;
    }
}
