package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.LobbyJoinInfo;
import be.kdg.int5.gameplay.port.in.AcceptInviteCommand;
import be.kdg.int5.gameplay.port.in.AcceptInviteUseCase;
import be.kdg.int5.gameplay.port.out.GameInviteLoadPort;
import be.kdg.int5.gameplay.port.out.GameInviteSavePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AcceptInviteUseCaseImpl implements AcceptInviteUseCase {
    private final Logger logger = LoggerFactory.getLogger(AcceptInviteUseCaseImpl.class);

    private final GameInviteLoadPort gameInviteLoadPort;
    private final GameInviteSavePort gameInviteSavePort;

    public AcceptInviteUseCaseImpl(GameInviteLoadPort gameInviteLoadPort, GameInviteSavePort gameInviteSavePort) {
        this.gameInviteLoadPort = gameInviteLoadPort;
        this.gameInviteSavePort = gameInviteSavePort;
    }

    @Override
    public LobbyJoinInfo acceptInviteAndGetJoinInfo(AcceptInviteCommand command) throws NoSuchInviteException, LobbyClosedException {
        GameInvite invite = gameInviteLoadPort.load(command.inviteId());

        if (invite == null || !invite.getInvited().uuid().equals(command.requestingPlayer().uuid())) {
            logger.warn("gameplay:acceptInvite Could not load invite {} for player {}!",
                    command.inviteId(),
                    command.requestingPlayer()
            );
            throw new NoSuchInviteException();
        }

        if (invite.getLobby() == null) {
            logger.error("gameplay:acceptInvite Invite {} has no lobby relationship!", invite.getId());
            throw new RuntimeException("Invite has no lobby!");
        }
        if (invite.getLobby().isClosed()) {
            logger.warn("gameplay:acceptInvite Player {} tried to join a closed lobby with id {}",
                    command.requestingPlayer(),
                    command.inviteId()
            );
            throw new LobbyClosedException();
        }

        invite.setAccepted(true);
        gameInviteSavePort.save(invite);

        return new LobbyJoinInfo(
                invite.getLobby().getId(),
                invite.getLobby().getGameId()
        );
    }
}
