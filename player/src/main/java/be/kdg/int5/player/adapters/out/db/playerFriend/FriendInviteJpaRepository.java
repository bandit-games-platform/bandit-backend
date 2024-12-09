package be.kdg.int5.player.adapters.out.db.playerFriend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface FriendInviteJpaRepository extends JpaRepository<FriendInviteJpaEntity, UUID> {
    @Query(
            """
            SELECT f FROM FriendInviteJpaEntity f\s
            WHERE (f.inviter.id = :userId1 AND f.invited.id = :userId2)
            OR (f.inviter.id = :userId2 AND f.invited.id = :userId1)
            """
    )
    FriendInviteJpaEntity findByPlayers(UUID userId1, UUID userId2);
    List<FriendInviteJpaEntity> getAllByInvited_Id(UUID invitedId);
    List<FriendInviteJpaEntity> getAllByInviter_Id(UUID invitedId);
}
