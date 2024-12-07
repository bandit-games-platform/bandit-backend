package be.kdg.int5.player.adapters.out.db.playerFriend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendsRelationJpaRepository extends JpaRepository<FriendsRelationJpaEntity, UUID> {

    @Query("""
        SELECT\s
            CASE\s
                WHEN pf.playerA.id = :playerId THEN pf.playerB.id
                ELSE pf.playerA.id
            END
        FROM FriendsRelationJpaEntity pf
        WHERE pf.playerA.id = :playerId\s
           OR pf.playerB.id = :playerId
    """)
    List<UUID> findAllByPlayerIdOrFriendId(UUID playerId);
}