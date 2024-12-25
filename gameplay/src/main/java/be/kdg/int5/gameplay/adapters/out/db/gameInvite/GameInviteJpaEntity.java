package be.kdg.int5.gameplay.adapters.out.db.gameInvite;

import be.kdg.int5.gameplay.adapters.out.db.lobby.LobbyJpaEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(schema = "gameplay", name = "game_invite")
public class GameInviteJpaEntity {
    @Id
    private UUID id;
    private UUID inviter;
    private UUID invited;
    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LobbyJpaEntity lobby;

    public GameInviteJpaEntity() {
    }

    public GameInviteJpaEntity(UUID id, UUID inviter, UUID invited, boolean accepted, LobbyJpaEntity lobby) {
        this.id = id;
        this.inviter = inviter;
        this.invited = invited;
        this.accepted = accepted;
        this.lobby = lobby;
    }

    public UUID getId() {
        return id;
    }

    public UUID getInviter() {
        return inviter;
    }

    public UUID getInvited() {
        return invited;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public LobbyJpaEntity getLobby() {
        return lobby;
    }
}
