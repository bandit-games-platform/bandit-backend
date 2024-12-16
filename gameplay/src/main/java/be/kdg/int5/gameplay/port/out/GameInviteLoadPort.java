package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.GameInviteId;
import be.kdg.int5.gameplay.domain.PlayerId;

import java.util.List;

public interface GameInviteLoadPort {
    GameInvite load(GameInviteId id);

    List<GameInvite> loadAllPendingByInvitedPlayerId(PlayerId invited);
}
