package be.kdg.int5.player.port.in;

import be.kdg.int5.player.domain.GameId;
import be.kdg.int5.player.domain.PlayerId;
import java.util.Objects;

public record FavoriteGameCommand(PlayerId playerId, GameId gameId, boolean newFavouriteStatus) {
    public FavoriteGameCommand {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(gameId);
    }
}
