package be.kdg.int5.gameplay.adapters.out.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class GameInviteJpaEntityId implements Serializable {
    @Column(name = "lobby_id", nullable = false)
    private UUID lobbyId;
    @Column(name = "invited_player_id", nullable = false)
    private UUID invitedPlayerId;

    public GameInviteJpaEntityId() {
    }

    public GameInviteJpaEntityId(UUID lobbyId, UUID invitedPlayerId) {
        this.lobbyId = lobbyId;
        this.invitedPlayerId = invitedPlayerId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public UUID getInvitedPlayerId() {
        return invitedPlayerId;
    }
}
