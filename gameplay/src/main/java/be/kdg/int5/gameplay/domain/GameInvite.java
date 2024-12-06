package be.kdg.int5.gameplay.domain;

import java.util.Objects;

public class GameInvite {
    private final PlayerId invited;
    private boolean accepted;

    public GameInvite(PlayerId invited) {
        this(invited, false);
    }

    public GameInvite(PlayerId invited, boolean accepted) {
        this.invited = Objects.requireNonNull(invited);
        this.accepted = accepted;
    }

    public PlayerId getInvited() {
        return invited;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
