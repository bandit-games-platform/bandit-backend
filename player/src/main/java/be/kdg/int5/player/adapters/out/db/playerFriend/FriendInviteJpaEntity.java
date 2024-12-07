package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.domain.InviteStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        schema = "player",
        name = "friend_invite_status",
        uniqueConstraints = @UniqueConstraint(columnNames = {"inviter_id", "invited_id"})
)
public class FriendInviteJpaEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inviter_id", nullable = false)
    private PlayerJpaEntity inviter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invited_id", nullable = false)
    private PlayerJpaEntity invited;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InviteStatus status;

    @Column(name = "time_of_invite", nullable = false)
    private LocalDateTime invitedTime;

    public FriendInviteJpaEntity() {
    }

    public FriendInviteJpaEntity(UUID id, PlayerJpaEntity inviter, PlayerJpaEntity invited, InviteStatus status, LocalDateTime invitedTime) {
        this.id = id;
        this.inviter = inviter;
        this.invited = invited;
        this.status = status;
        this.invitedTime = invitedTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlayerJpaEntity getInviter() {
        return inviter;
    }

    public void setInviter(PlayerJpaEntity inviter) {
        this.inviter = inviter;
    }

    public PlayerJpaEntity getInvited() {
        return invited;
    }

    public void setInvited(PlayerJpaEntity invited) {
        this.invited = invited;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
    }

    public LocalDateTime getInvitedTime() {
        return invitedTime;
    }

    public void setInvitedTime(LocalDateTime invitedTime) {
        this.invitedTime = invitedTime;
    }
}
