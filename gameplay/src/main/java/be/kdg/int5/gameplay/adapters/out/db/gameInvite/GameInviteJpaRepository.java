package be.kdg.int5.gameplay.adapters.out.db.gameInvite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameInviteJpaRepository extends JpaRepository<GameInviteJpaEntity, UUID> {
    @Query("""
    SELECT gameInvite FROM GameInviteJpaEntity gameInvite
    LEFT JOIN FETCH gameInvite.lobby
    WHERE gameInvite.id = :gameInviteId
    """)
    Optional<GameInviteJpaEntity> findByIdWithLobby(UUID gameInviteId);

    @Query("""
    SELECT gameInvite FROM GameInviteJpaEntity gameInvite
    LEFT JOIN FETCH gameInvite.lobby
    WHERE gameInvite.invited = :invitedId AND NOT gameInvite.accepted
    """)
    List<GameInviteJpaEntity> findAllByInvitedAndAcceptedIsFalseWithLobby(UUID invitedId);
}
