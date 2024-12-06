package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.PatchLobbyCommand;
import be.kdg.int5.gameplay.port.in.PatchLobbyUseCase;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import be.kdg.int5.gameplay.port.out.LobbySavePort;
import org.springframework.stereotype.Service;

@Service
public class PatchLobbyUseCaseImpl implements PatchLobbyUseCase {
    private final LobbyLoadPort lobbyLoadPort;
    private final LobbySavePort lobbySavePort;

    public PatchLobbyUseCaseImpl(LobbyLoadPort lobbyLoadPort, LobbySavePort lobbySavePort) {
        this.lobbyLoadPort = lobbyLoadPort;
        this.lobbySavePort = lobbySavePort;
    }

    @Override
    public boolean patch(PatchLobbyCommand command) {
        LobbyId lobbyId = new LobbyId(command.lobbyId());
        Lobby existingLobby = lobbyLoadPort.loadWithoutInvites(lobbyId);

        if (existingLobby == null) return false;

        existingLobby.patch(
                command.ownerId(),
                command.currentPlayerCount(),
                command.closed()
        );

        lobbySavePort.save(existingLobby);
        return true;
    }
}
