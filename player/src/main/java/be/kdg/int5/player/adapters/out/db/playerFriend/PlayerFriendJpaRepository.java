package be.kdg.int5.player.adapters.out.db.playerFriend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerFriendJpaRepository extends JpaRepository<PlayerFriendsJpaEntity, UUID> {

    List<PlayerFriendsJpaEntity> findAllByPlayer_Id(UUID playerId);
}