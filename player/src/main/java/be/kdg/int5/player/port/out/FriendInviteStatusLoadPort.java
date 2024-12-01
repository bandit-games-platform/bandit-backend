package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.FriendInviteId;

public interface FriendInviteStatusLoadPort {
    FriendInvite loadFriendInviteStatus(FriendInviteId friendInviteId);
}
