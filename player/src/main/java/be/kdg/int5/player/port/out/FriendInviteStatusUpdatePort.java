package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.PlayerId;

public interface FriendInviteStatusUpdatePort {
    boolean updateFriendInviteStatusToAccepted(FriendInviteId friendInviteId, PlayerId playerId);
    boolean updateFriendInviteStatusToRejected(FriendInviteId friendInviteId, PlayerId playerId);
}
