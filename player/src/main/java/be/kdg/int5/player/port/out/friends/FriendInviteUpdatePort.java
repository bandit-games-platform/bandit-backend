package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.PlayerId;

public interface FriendInviteUpdatePort {
    void updateFriendInvite(FriendInvite FriendInvite);
}
