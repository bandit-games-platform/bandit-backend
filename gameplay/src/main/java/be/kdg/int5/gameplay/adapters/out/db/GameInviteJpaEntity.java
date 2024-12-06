package be.kdg.int5.gameplay.adapters.out.db;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(schema = "gameplay", name = "game_invite")
public class GameInviteJpaEntity {
    @EmbeddedId
    private GameInviteJpaEntityId id;
    private boolean accepted;

    public GameInviteJpaEntity() {
    }

    public GameInviteJpaEntity(GameInviteJpaEntityId id, boolean accepted) {
        this.id = id;
        this.accepted = accepted;
    }

    public GameInviteJpaEntityId getId() {
        return id;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
