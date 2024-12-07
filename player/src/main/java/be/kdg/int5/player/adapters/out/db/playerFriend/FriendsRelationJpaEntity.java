package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "player", name = "friends")
public class FriendsRelationJpaEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_a_id", nullable = false)
    private PlayerJpaEntity playerA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_b_id", nullable = false)
    private PlayerJpaEntity playerB;
    private LocalDateTime friendshipStartDate;

    public FriendsRelationJpaEntity() {
    }

    public FriendsRelationJpaEntity(UUID id, PlayerJpaEntity playerA, PlayerJpaEntity playerB, LocalDateTime friendshipStartDate) {
        this.id = id;
        this.playerA = playerA;
        this.playerB = playerB;
        this.friendshipStartDate = friendshipStartDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerJpaEntity getPlayerA() {
        return playerA;
    }

    public void setPlayerA(PlayerJpaEntity player) {
        this.playerA = player;
    }

    public PlayerJpaEntity getPlayerB() {
        return playerB;
    }

    public void setPlayerB(PlayerJpaEntity friend) {
        this.playerB = friend;
    }

    public LocalDateTime getFriendshipStartDate() {
        return friendshipStartDate;
    }

    public void setFriendshipStartDate(LocalDateTime friendshipStartDate) {
        this.friendshipStartDate = friendshipStartDate;
    }
}