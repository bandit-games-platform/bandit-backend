package be.kdg.int5.player.adapters.out.db.playerFriend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendInviteStatusJpaRepository extends JpaRepository<FriendInviteStatusJpaEntity, UUID> {
    FriendInviteStatusJpaEntity findByInviter_IdAndInvited_Id(UUID inviterId, UUID invitedId);
}
