package be.kdg.int5.player.port.in;
import be.kdg.int5.player.domain.PlayerId;

import java.util.Objects;

public record SearchForNewFriendsCommand(String username, PlayerId playerId) {
    public SearchForNewFriendsCommand {
        Objects.requireNonNull(username);
        Objects.requireNonNull(playerId);
    }
}
