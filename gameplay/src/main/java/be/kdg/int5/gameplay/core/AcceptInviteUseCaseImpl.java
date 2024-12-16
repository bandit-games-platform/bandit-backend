package be.kdg.int5.gameplay.core;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.LobbyJoinInfo;
import be.kdg.int5.gameplay.port.in.AcceptInviteCommand;
import be.kdg.int5.gameplay.port.in.AcceptInviteUseCase;
import be.kdg.int5.gameplay.port.out.GameInviteLoadPort;
import be.kdg.int5.gameplay.port.out.GameInviteSavePort;
import org.springframework.stereotype.Service;

@Service
public class AcceptInviteUseCaseImpl implements AcceptInviteUseCase {
    private final GameInviteLoadPort gameInviteLoadPort;
    private final GameInviteSavePort gameInviteSavePort;

    public AcceptInviteUseCaseImpl(GameInviteLoadPort gameInviteLoadPort, GameInviteSavePort gameInviteSavePort) {
        this.gameInviteLoadPort = gameInviteLoadPort;
        this.gameInviteSavePort = gameInviteSavePort;
    }

    @Override
    public LobbyJoinInfo acceptInviteAndGetJoinInfo(AcceptInviteCommand command) throws NoSuchInviteException, LobbyClosedException {
        GameInvite invite = gameInviteLoadPort.load(command.inviteId());

        if (invite == null || !invite.getInvited().uuid().equals(command.requestingPlayer().uuid())) throw new NoSuchInviteException();

        if (invite.getLobby() == null) throw new RuntimeException("Invite has no lobby!");
        if (invite.getLobby().isClosed()) throw new LobbyClosedException();

        invite.setAccepted(true);
        gameInviteSavePort.save(invite);

        return new LobbyJoinInfo(
                invite.getLobby().getId(),
                invite.getLobby().getGameId()
        );
    }
}
