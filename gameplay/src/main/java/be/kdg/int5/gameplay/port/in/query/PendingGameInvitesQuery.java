package be.kdg.int5.gameplay.port.in.query;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.PlayerId;

import java.util.List;

public interface PendingGameInvitesQuery {
    List<GameInvite> getAllPendingGameInvitesForPlayer(PlayerId invitedPlayer);
}
