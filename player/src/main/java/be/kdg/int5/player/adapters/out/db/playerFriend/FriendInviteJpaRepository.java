package be.kdg.int5.player.adapters.out.db.playerFriend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendInviteJpaRepository extends JpaRepository<FriendInviteJpaEntity, UUID> {
    FriendInviteJpaEntity findByInviter_IdAndInvited_Id(UUID inviterId, UUID invitedId);
    List<FriendInviteJpaEntity> getAllByInvited_Id(UUID invitedId);
    List<FriendInviteJpaEntity> getAllByInviter_Id(UUID invitedId);
}
