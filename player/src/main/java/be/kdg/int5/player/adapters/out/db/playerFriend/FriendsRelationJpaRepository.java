package be.kdg.int5.player.adapters.out.db.playerFriend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerFriendsRelationJpaRepository extends JpaRepository<PlayerFriendsRelationJpaEntity, UUID> {

    @Query("""
        SELECT\s
            CASE\s
                WHEN pf.player.id = :playerId THEN pf.friend.id
                ELSE pf.player.id
            END
        FROM PlayerFriendsRelationJpaEntity pf
        WHERE pf.player.id = :playerId\s
           OR pf.friend.id = :playerId
    """)
    List<UUID> findAllByPlayerIdOrFriendId(UUID playerId);
}