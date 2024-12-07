package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "player", name = "friends")
public class PlayerFriendsRelationJpaEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private PlayerJpaEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private PlayerJpaEntity friend;

    public PlayerFriendsRelationJpaEntity() {
    }

    public PlayerFriendsRelationJpaEntity(UUID id, PlayerJpaEntity player, PlayerJpaEntity friend) {
        this.id = id;
        this.player = player;
        this.friend = friend;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerJpaEntity player) {
        this.player = player;
    }

    public PlayerJpaEntity getFriend() {
        return friend;
    }

    public void setFriend(PlayerJpaEntity friend) {
        this.friend = friend;
    }
}