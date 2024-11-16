package be.kdg.int5.gameplay.domain;

public class GameInvite {
    private final PlayerId invited;
    private boolean accepted;

    public GameInvite(PlayerId invited) {
        this.accepted = false;
        this.invited = invited;
    }
}
