package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.out.FriendsListLoadPort;
import be.kdg.int5.player.port.out.PlayerUsernameLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PlayerFriendJpaAdapter implements PlayerUsernameLoadPort, FriendsListLoadPort {
    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerFriendJpaRepository playerFriendJpaRepository;

    public PlayerFriendJpaAdapter(final PlayerJpaRepository playerJpaRepository,
                                  final PlayerFriendJpaRepository playerFriendJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
        this.playerFriendJpaRepository = playerFriendJpaRepository;
    }

    @Override
    public List<Player> loadSearchPlayersByUsernameExcludingFriends(String username, PlayerId playerId) {
        List<PlayerJpaEntity> playerJpaEntities = playerJpaRepository.findByDisplayNameContainingIgnoreCase(username);
        if (playerJpaEntities == null || playerJpaEntities.isEmpty()) return null;

        // Get the list of friends of the player
        List<Player> friends = getAllFriendsOfPlayer(playerId);

        // Filter the players to exclude any that are already friends
        return playerJpaEntities.stream()
                .map(this::playerJpaToDomain)
                .filter(player -> friends == null || friends.stream().noneMatch(friend -> friend.getId().equals(player.getId())))
                .toList();
    }

    private Player playerJpaToDomain(PlayerJpaEntity playerJpaEntity){
        return new Player(
                new PlayerId(playerJpaEntity.getId()),
                playerJpaEntity.getDisplayName(),
                new ImageResource(new ResourceURL(playerJpaEntity.getAvatar().getUrl())));
    }

    @Override
    public List<Player> getAllFriendsOfPlayer(PlayerId playerId) {
        List<PlayerFriendsJpaEntity> playerFriendsJpaEntities = playerFriendJpaRepository.findAllByPlayer_Id(playerId.uuid());
        if (playerFriendsJpaEntities.isEmpty()) return null;
        List<UUID> playerJpaEntitiesUUID = playerFriendsJpaEntities.stream()
                .map(playerFriendsJpaEntity -> playerFriendsJpaEntity.getFriend().getId())
                .toList();
        return playerJpaRepository.findAllById(playerJpaEntitiesUUID)
                .stream()
                .map(this::playerJpaToDomain)
                .toList();
    }
}
