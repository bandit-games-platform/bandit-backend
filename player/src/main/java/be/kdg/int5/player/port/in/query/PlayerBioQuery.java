package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.domain.Player;

import java.util.List;

public interface PlayerBioQuery {
    Player getPlayerBio(GetPlayerBioCommand command);
}
