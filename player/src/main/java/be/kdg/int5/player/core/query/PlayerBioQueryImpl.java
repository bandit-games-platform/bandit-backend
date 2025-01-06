package be.kdg.int5.player.core.query;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.query.FriendsListQuery;
import be.kdg.int5.player.port.in.query.GetFriendsListCommand;
import be.kdg.int5.player.port.in.query.GetPlayerBioCommand;
import be.kdg.int5.player.port.in.query.PlayerBioQuery;
import be.kdg.int5.player.port.out.PlayerLoadPort;
import be.kdg.int5.player.port.out.friends.FriendsListLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerBioQueryImpl implements PlayerBioQuery {
    private final PlayerLoadPort playerLoadPort;

    public PlayerBioQueryImpl(PlayerLoadPort playerLoadPort) {
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Player getPlayerBio(GetPlayerBioCommand command) {
        return playerLoadPort.loadPlayerBioById(command.playerId().uuid());
    }
}
