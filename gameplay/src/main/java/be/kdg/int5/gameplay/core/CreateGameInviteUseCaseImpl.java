package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.port.in.CreateGameInviteCommand;
import be.kdg.int5.gameplay.port.in.CreateGameInviteUseCase;
import be.kdg.int5.gameplay.port.out.GameInviteSavePort;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import org.springframework.stereotype.Service;

@Service
public class CreateGameInviteUseCaseImpl implements CreateGameInviteUseCase {
    private final GameInviteSavePort gameInviteSavePort;
    private final LobbyLoadPort lobbyLoadPort;

    public CreateGameInviteUseCaseImpl(GameInviteSavePort gameInviteSavePort, LobbyLoadPort lobbyLoadPort) {
        this.gameInviteSavePort = gameInviteSavePort;
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public boolean createInvite(CreateGameInviteCommand command) {
        Lobby lobby = lobbyLoadPort.load(command.lobbyId());
        if (lobby == null) return false;

        gameInviteSavePort.save(new GameInvite(
                command.inviterId(),
                command.invitedId(),
                lobby
        ));
        return true;
    }
}
