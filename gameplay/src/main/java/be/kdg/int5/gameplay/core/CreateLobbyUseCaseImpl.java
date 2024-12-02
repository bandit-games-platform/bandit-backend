package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import be.kdg.int5.gameplay.port.out.LobbySavePort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateLobbyUseCaseImpl implements CreateLobbyUseCase {
    private final LobbyLoadPort lobbyLoadPort;
    private final LobbySavePort lobbySavePort;

    public CreateLobbyUseCaseImpl(LobbyLoadPort lobbyLoadPort, LobbySavePort lobbySavePort) {
        this.lobbyLoadPort = lobbyLoadPort;
        this.lobbySavePort = lobbySavePort;
    }

    @Override
    public LobbyId create(CreateLobbyCommand command) {
        LobbyId newLobbyId = new LobbyId(UUID.randomUUID());

        if (lobbyLoadPort.loadWithoutInvites(newLobbyId) != null) {
            return null;
        }

        Lobby newLobby = new Lobby(
                newLobbyId,
                new GameId(command.gameId()),
                command.maxPlayers(),
                new PlayerId(command.ownerId())
        );

        lobbySavePort.save(newLobby);
        return newLobbyId;
    }
}
