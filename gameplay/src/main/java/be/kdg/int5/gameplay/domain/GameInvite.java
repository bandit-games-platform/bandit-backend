package be.kdg.int5.gameplay.domain;

import java.util.Objects;

public class GameInvite {
    private final PlayerId invited;
    private boolean accepted;

    public GameInvite(PlayerId invited) {
        this.accepted = false;
        this.invited = Objects.requireNonNull(invited);
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
