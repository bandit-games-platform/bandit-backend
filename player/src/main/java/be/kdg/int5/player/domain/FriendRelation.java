package be.kdg.int5.player.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class FriendRelation {
    private final UUID id;
    private final Player friendA;
    private final Player friendB;
    private final LocalDateTime friendshipStartDate;

    public FriendRelation(final Player friendA, final Player friendB) {
        this.id = UUID.randomUUID();
        this.friendA = friendA;
        this.friendB = friendB;
        this.friendshipStartDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Player getFriendA() {
        return friendA;
    }

    public Player getFriendB() {
        return friendB;
    }

    public LocalDateTime getFriendshipStartDate() {
        return friendshipStartDate;
    }
}
