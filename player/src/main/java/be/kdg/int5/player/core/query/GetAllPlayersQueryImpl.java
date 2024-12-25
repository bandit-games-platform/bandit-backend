package be.kdg.int5.player.core.query;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.query.FriendsListQuery;
import be.kdg.int5.player.port.in.query.GetAllPlayersQuery;
import be.kdg.int5.player.port.in.query.GetFriendsListCommand;
import be.kdg.int5.player.port.out.PlayerLoadPort;
import be.kdg.int5.player.port.out.friends.FriendsListLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllPlayersQueryImpl implements GetAllPlayersQuery {
    private final PlayerLoadPort playerLoadPort;

    public GetAllPlayersQueryImpl(PlayerLoadPort playerLoadPort) {
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerLoadPort.loadAllPlayers();
    }
}
