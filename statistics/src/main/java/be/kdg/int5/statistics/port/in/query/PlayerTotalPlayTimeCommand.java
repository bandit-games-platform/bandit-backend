package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.PlayerId;

public record PlayerTotalPlayTimeCommand(PlayerId playerId) {
}
