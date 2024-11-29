package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.query.GetPlayerJoinDateCommand;
import be.kdg.int5.player.port.in.query.GetPlayerJoinDateQuery;
import be.kdg.int5.player.port.out.PlayerLoadPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GetPlayerJoinDateQueryImpl implements GetPlayerJoinDateQuery {
    private final PlayerLoadPort playerLoadPort;

    public GetPlayerJoinDateQueryImpl(PlayerLoadPort playerLoadPort) {
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    public LocalDateTime getPlayerJoinDate(GetPlayerJoinDateCommand command) {
        Player player = playerLoadPort.loadPlayerById(command.playerId().uuid());
        if (player == null) return null;
        return player.getJoinDate();
    }
}
