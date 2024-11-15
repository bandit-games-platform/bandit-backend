package be.kdg.int5.gameplay.domain;

public class Invite {
    private PlayerId invited;
    private boolean accepted;

    public Invite(PlayerId invited) {
        this.invited = invited;
    }
}
