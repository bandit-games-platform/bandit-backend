package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.port.in.DismissInviteCommand;
import be.kdg.int5.gameplay.port.in.DismissInviteUseCase;
import be.kdg.int5.gameplay.port.out.GameInviteDeletePort;
import be.kdg.int5.gameplay.port.out.GameInviteLoadPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DismissInviteUseCaseImpl implements DismissInviteUseCase {
    private final Logger logger = LoggerFactory.getLogger(DismissInviteUseCaseImpl.class);

    private final GameInviteDeletePort gameInviteDeletePort;
    private final GameInviteLoadPort gameInviteLoadPort;

    public DismissInviteUseCaseImpl(GameInviteDeletePort gameInviteDeletePort, GameInviteLoadPort gameInviteLoadPort) {
        this.gameInviteDeletePort = gameInviteDeletePort;
        this.gameInviteLoadPort = gameInviteLoadPort;
    }

    @Override
    @Transactional
    public boolean dismissInvite(DismissInviteCommand command) {
        GameInvite invite = gameInviteLoadPort.load(command.inviteId());

        if (invite == null) {
            logger.error("gameplay:dismissInvite Could not dismiss invite with id '{}', invite was null.",
                    command.inviteId()
            );
            return false;
        }

        if (!invite.getInvited().uuid().equals(command.requestingPlayer().uuid())) {
            logger.warn("gameplay:dismissInvite Did not allow '{}' to dismiss invite '{}', because it belongs to '{}'.",
                    command.requestingPlayer(),
                    command.inviteId(),
                    invite.getInvited()
            );
            return false;
        }

        gameInviteDeletePort.deleteById(invite.getId());
        return true;
    }
}
