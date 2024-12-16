package be.kdg.int5.gameplay.core.query;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.query.PendingGameInvitesQuery;
import be.kdg.int5.gameplay.port.out.GameInviteLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingGameInvitesQueryImpl implements PendingGameInvitesQuery {
    private final GameInviteLoadPort gameInviteLoadPort;

    public PendingGameInvitesQueryImpl(GameInviteLoadPort gameInviteLoadPort) {
        this.gameInviteLoadPort = gameInviteLoadPort;
    }

    @Override
    public List<GameInvite> getAllPendingGameInvitesForPlayer(PlayerId invitedPlayer) {
        return gameInviteLoadPort.loadAllPendingByInvitedPlayerId(invitedPlayer);
    }
}
