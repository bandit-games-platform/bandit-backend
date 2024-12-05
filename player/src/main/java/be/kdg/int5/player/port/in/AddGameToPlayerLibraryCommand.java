package be.kdg.int5.player.port.in;

import be.kdg.int5.player.domain.GameId;
import be.kdg.int5.player.domain.PlayerId;

public record AddGameToPlayerLibraryCommand(PlayerId playerId, GameId gameId) {
}
