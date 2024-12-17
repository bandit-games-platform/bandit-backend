package be.kdg.int5.gameplay.domain;

import java.util.Objects;
import java.util.UUID;

public class GameInvite {
    private final GameInviteId id;
    private final PlayerId inviter;
    private final PlayerId invited;
    private final Lobby lobby;
    private boolean accepted;

    public GameInvite(PlayerId inviter, PlayerId invited, Lobby lobby) {
        this(new GameInviteId(UUID.randomUUID()), inviter, invited, lobby, false);
    }

    public GameInvite(GameInviteId id, PlayerId inviter, PlayerId invited, Lobby lobby, boolean accepted) {
        this.id = Objects.requireNonNull(id);
        this.inviter = Objects.requireNonNull(inviter);
        this.invited = Objects.requireNonNull(invited);
        this.lobby = lobby;
        this.accepted = accepted;
    }

    public GameInviteId getId() {
        return id;
    }

    public PlayerId getInviter() {
        return inviter;
    }

    public PlayerId getInvited() {
        return invited;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
