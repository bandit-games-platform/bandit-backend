package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.domain.PlayerId;

import java.util.Objects;

public record GetPlayerBioCommand(PlayerId playerId) {
    public GetPlayerBioCommand {
        Objects.requireNonNull(playerId);
    }
}
