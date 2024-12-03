package be.kdg.int5.player.core.query;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.SearchForNewFriendsCommand;
import be.kdg.int5.player.port.in.SearchForNewFriendsUseCase;
import be.kdg.int5.player.port.out.PlayerUsernameLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchForNewFriendsUseCaseImpl implements SearchForNewFriendsUseCase {
    private final PlayerUsernameLoadPort playerUsernameLoadPort;

    public SearchForNewFriendsUseCaseImpl(PlayerUsernameLoadPort playerUsernameLoadPort) {
        this.playerUsernameLoadPort = playerUsernameLoadPort;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Player> searchForNewFriends(SearchForNewFriendsCommand command) {
        return playerUsernameLoadPort.loadSearchPlayersByUsernameExcludingFriends(command.username(), command.playerId());
    }
}
