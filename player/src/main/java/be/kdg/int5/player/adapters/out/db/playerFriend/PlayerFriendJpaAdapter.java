package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.common.exceptions.InviteStatusExists;
import be.kdg.int5.player.adapters.out.db.player.ImageResourceJpaEmbed;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.out.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class PlayerFriendJpaAdapter implements PlayerUsernameLoadPort, FriendsListLoadPort, FriendInviteStatusCreatePort, FriendInviteStatusLoadPort, FriendInviteStatusUpdatePort {
    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerFriendJpaRepository playerFriendJpaRepository;
    private final FriendInviteStatusJpaRepository friendInviteStatusJpaRepository;

    public PlayerFriendJpaAdapter(final PlayerJpaRepository playerJpaRepository,
                                  final PlayerFriendJpaRepository playerFriendJpaRepository,
                                  final FriendInviteStatusJpaRepository friendInviteStatusJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
        this.playerFriendJpaRepository = playerFriendJpaRepository;
        this.friendInviteStatusJpaRepository = friendInviteStatusJpaRepository;
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

    @Override
    public FriendInvite createFriendInviteStatus(FriendInvite friendInvite) {
        PlayerJpaEntity inviter = playerJpaRepository.getReferenceById(friendInvite.getInviter().uuid());
        PlayerJpaEntity invited = playerJpaRepository.getReferenceById(friendInvite.getInvited().uuid());

        FriendInviteStatusJpaEntity oldFriendInviteStatusJpaEntity = friendInviteStatusJpaRepository.findByInviter_IdAndInvited_Id(inviter.getId(), invited.getId());
        if (oldFriendInviteStatusJpaEntity != null) throw new InviteStatusExists();
        FriendInviteStatusJpaEntity friendInviteStatusJpaEntity = new FriendInviteStatusJpaEntity(
                friendInvite.getId().uuid(),
                inviter,
                invited,
                friendInvite.getStatus(),
                LocalDateTime.now()
        );
        friendInviteStatusJpaRepository.save(friendInviteStatusJpaEntity);
        return friendInviteJpaToDomain(friendInviteStatusJpaEntity);
    }

    @Override
    public FriendInvite loadFriendInviteStatus(FriendInviteId friendInviteId) {
        FriendInviteStatusJpaEntity friendInviteStatusJpaEntity = friendInviteStatusJpaRepository.getReferenceById(friendInviteId.uuid());
        return this.friendInviteJpaToDomain(friendInviteStatusJpaEntity);
    }

    @Override
    public void updateFriendInviteStatusToAccepted(FriendInviteId friendInviteId) {
        FriendInviteStatusJpaEntity friendInviteStatusJpaEntity = friendInviteStatusJpaRepository.getReferenceById(friendInviteId.uuid());
        FriendInvite friendInvite = this.friendInviteJpaToDomain(friendInviteStatusJpaEntity);
        friendInvite.setStatusToAccepted();
        friendInviteStatusJpaRepository.save(this.friendInviteDomainToJpa(friendInvite));
    }

    @Override
    public void updateFriendInviteStatusToRejected(FriendInviteId friendInviteId) {
        FriendInviteStatusJpaEntity friendInviteStatusJpaEntity = friendInviteStatusJpaRepository.getReferenceById(friendInviteId.uuid());
        FriendInvite friendInvite = this.friendInviteJpaToDomain(friendInviteStatusJpaEntity);
        friendInvite.setStatusToRejected();
        friendInviteStatusJpaRepository.save(this.friendInviteDomainToJpa(friendInvite));
    }

    private FriendInvite friendInviteJpaToDomain(FriendInviteStatusJpaEntity friendInviteStatusJpaEntity){
       return new FriendInvite(
                new FriendInviteId(friendInviteStatusJpaEntity.getId()),
                new PlayerId(friendInviteStatusJpaEntity.getInviter().getId()),
                new PlayerId(friendInviteStatusJpaEntity.getInvited().getId()),
                friendInviteStatusJpaEntity.getStatus(),
                friendInviteStatusJpaEntity.getInvitedTime()
        );
    }

    private FriendInviteStatusJpaEntity friendInviteDomainToJpa(FriendInvite friendInvite){
        PlayerJpaEntity inviter = playerJpaRepository.getReferenceById(friendInvite.getInviter().uuid());
        PlayerJpaEntity invited = playerJpaRepository.getReferenceById(friendInvite.getInvited().uuid());
        return new FriendInviteStatusJpaEntity(
                friendInvite.getId().uuid(),
                inviter,
                invited,
                friendInvite.getStatus(),
                friendInvite.getInvitedTime()
        );
    }

    private Player playerJpaToDomain(PlayerJpaEntity playerJpaEntity) {
        return new Player(
                new PlayerId(playerJpaEntity.getId()),
                playerJpaEntity.getDisplayName(),
                new ImageResource(new ResourceURL(playerJpaEntity.getAvatar().getUrl())));
    }

    private PlayerJpaEntity playerDomainToJpa(Player player) {
        return new PlayerJpaEntity(
                player.getId().uuid(),
                player.getDisplayName(),
                new ImageResourceJpaEmbed(player.getAvatar().url().url())
        );
    }
}
