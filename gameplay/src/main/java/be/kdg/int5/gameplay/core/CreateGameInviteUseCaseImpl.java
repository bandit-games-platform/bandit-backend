package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.port.in.CreateGameInviteCommand;
import be.kdg.int5.gameplay.port.in.CreateGameInviteUseCase;
import be.kdg.int5.gameplay.port.out.GameInviteSavePort;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateGameInviteUseCaseImpl implements CreateGameInviteUseCase {
    private final Logger logger = LoggerFactory.getLogger(CreateGameInviteUseCaseImpl.class);

    private final GameInviteSavePort gameInviteSavePort;
    private final LobbyLoadPort lobbyLoadPort;

    public CreateGameInviteUseCaseImpl(GameInviteSavePort gameInviteSavePort, LobbyLoadPort lobbyLoadPort) {
        this.gameInviteSavePort = gameInviteSavePort;
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public boolean createInvite(CreateGameInviteCommand command) {
        Lobby lobby = lobbyLoadPort.load(command.lobbyId());
        if (lobby == null) {
            logger.warn("gameplay:createInvite Failed to create invite for '{}', lobby was null.", command.lobbyId());
            return false;
        }

        if (!lobby.getOwnerId().equals(command.inviterId())) {
            logger.warn("gameplay:createInvite Player {} tried to invite {} to lobby {}, but lobby is owned by {}",
                    command.inviterId(),
                    command.invitedId(),
                    command.lobbyId(),
                    lobby.getOwnerId()
            );
            return false;
        }

        gameInviteSavePort.save(new GameInvite(
                command.inviterId(),
                command.invitedId(),
                lobby
        ));
        logger.info("gameplay:createInvite {} invited {} to lobby {}",
                command.invitedId(),
                command.invitedId(),
                command.lobbyId()
        );
        return true;
    }
}
