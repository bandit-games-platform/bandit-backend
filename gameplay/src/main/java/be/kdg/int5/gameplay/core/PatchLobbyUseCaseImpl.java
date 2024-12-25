package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.PatchLobbyCommand;
import be.kdg.int5.gameplay.port.in.PatchLobbyUseCase;
import be.kdg.int5.gameplay.port.out.LobbyDeletePort;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import be.kdg.int5.gameplay.port.out.LobbySavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PatchLobbyUseCaseImpl implements PatchLobbyUseCase {
    private final Logger logger = LoggerFactory.getLogger(PatchLobbyUseCaseImpl.class);

    private final LobbyLoadPort lobbyLoadPort;
    private final LobbySavePort lobbySavePort;
    private final LobbyDeletePort lobbyDeletePort;

    public PatchLobbyUseCaseImpl(LobbyLoadPort lobbyLoadPort, LobbySavePort lobbySavePort, LobbyDeletePort lobbyDeletePort) {
        this.lobbyLoadPort = lobbyLoadPort;
        this.lobbySavePort = lobbySavePort;
        this.lobbyDeletePort = lobbyDeletePort;
    }

    @Override
    public boolean patch(PatchLobbyCommand command) {
        LobbyId lobbyId = new LobbyId(command.lobbyId());
        Lobby existingLobby = lobbyLoadPort.load(lobbyId);

        if (existingLobby == null) {
            logger.warn("gameplay:patch-lobby Tried to patch lobby that does not exist: '{}'", command.lobbyId());
            return false;
        }

        if (command.ownerId() != null) {
            // Enforce one active lobby per owner per game for the new owner
            Lobby previousLobby = lobbyLoadPort.loadByOwnerIdAndGameId(
                    existingLobby.getOwnerId(),
                    existingLobby.getGameId()
            );
            if (previousLobby != null) {
                lobbyDeletePort.deleteById(previousLobby.getId());
                logger.warn("gameplay:patch-lobby Deleted previously existing lobby for owner '{}' and game '{}' with id: '{}'",
                        existingLobby.getOwnerId(),
                        existingLobby.getGameId(),
                        previousLobby.getId()
                );
            }
        }

        existingLobby.patch(
                command.ownerId(),
                command.currentPlayerCount(),
                command.closed()
        );

        lobbySavePort.save(existingLobby);
        logger.info("gameplay:patch-lobby Patched lobby '{}' with {ownerId: {}, playerCount: {}, isClosed: {}}",
                existingLobby.getId(),
                command.ownerId(),
                command.currentPlayerCount(),
                command.closed()
        );
        return true;
    }
}
