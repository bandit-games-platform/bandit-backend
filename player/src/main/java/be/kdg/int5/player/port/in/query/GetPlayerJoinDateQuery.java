package be.kdg.int5.player.port.in.query;

import java.time.LocalDateTime;

public interface GetPlayerJoinDateQuery {
    LocalDateTime getPlayerJoinDate(GetPlayerJoinDateCommand command);
}
