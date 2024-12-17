package be.kdg.int5.gameplay.port.in;

import be.kdg.int5.gameplay.domain.GameInviteId;
import be.kdg.int5.gameplay.domain.PlayerId;

import java.util.Objects;

public record DismissInviteCommand(GameInviteId inviteId, PlayerId requestingPlayer) {
    public DismissInviteCommand {
        Objects.requireNonNull(inviteId);
        Objects.requireNonNull(requestingPlayer);
    }
}
