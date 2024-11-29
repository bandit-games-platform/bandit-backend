package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.domain.PlayerId;

public record GetPlayerJoinDateCommand(PlayerId playerId) {
}
