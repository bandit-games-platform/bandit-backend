package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.common.exceptions.InviteStatusExistsException;
import be.kdg.int5.common.exceptions.UnauthorizedPlayerException;
import be.kdg.int5.player.domain.FriendInviteBio;
import be.kdg.int5.player.adapters.out.db.player.ImageResourceJpaEmbed;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.*;
import be.kdg.int5.player.port.out.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerFriendJpaAdapter implements PlayerUsernameLoadPort, FriendsListLoadPort, FriendInviteStatusCreatePort, FriendInviteStatusLoadPort, FriendInviteStatusUpdatePort {
    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerFriendJpaRepository playerFriendJpaRepository;
    private final FriendInviteJpaRepository friendInviteJpaRepository;

    public PlayerFriendJpaAdapter(final PlayerJpaRepository playerJpaRepository,
                                  final PlayerFriendJpaRepository playerFriendJpaRepository,
                                  final FriendInviteJpaRepository friendInviteJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
        this.playerFriendJpaRepository = playerFriendJpaRepository;
        this.friendInviteJpaRepository = friendInviteJpaRepository;
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
        List<UUID> playerJpaEntitiesUUID = playerFriendJpaRepository.findAllByPlayerIdOrFriendId(playerId.uuid());
        return playerJpaRepository.findAllById(playerJpaEntitiesUUID)
                .stream()
                .map(this::playerJpaToDomain)
                .toList();
    }

    @Override
    @Transactional
    public FriendInvite createFriendInviteStatus(FriendInvite friendInvite) {
        PlayerJpaEntity inviter = playerJpaRepository.getReferenceById(friendInvite.getInviter().uuid());
        PlayerJpaEntity invited = playerJpaRepository.getReferenceById(friendInvite.getInvited().uuid());

        FriendInviteJpaEntity oldFriendInviteJpaEntity = friendInviteJpaRepository.findByInviter_IdAndInvited_Id(inviter.getId(), invited.getId());
        if (oldFriendInviteJpaEntity != null) throw new InviteStatusExistsException();
        FriendInviteJpaEntity friendInviteJpaEntity = new FriendInviteJpaEntity(
                friendInvite.getId().uuid(),
                inviter,
                invited,
                friendInvite.getStatus(),
                LocalDateTime.now()
        );
        friendInviteJpaRepository.save(friendInviteJpaEntity);
        return friendInviteJpaToDomain(friendInviteJpaEntity);
    }

    @Override
    public List<FriendInviteBio> loadAllReceivedPendingFriendInvitesForPlayer(PlayerId playerId) {
        List<FriendInviteJpaEntity> friendInviteJpaEntities = friendInviteJpaRepository.getAllByInvited_Id(playerId.uuid());
        if (friendInviteJpaEntities.isEmpty()) return null;

       return friendInviteJpaEntities.stream()
                .filter(invite -> invite.getStatus() == InviteStatus.PENDING) // Filter pending invites
                .map(invite -> {
                    Optional<PlayerJpaEntity> inviterEntity = playerJpaRepository.findById(invite.getInviter().getId());
                    return inviterEntity.map(player -> mapToFriendInviteBioDto(playerJpaToDomain(player), invite));
                })
                .filter(Optional::isPresent) // Remove empty results
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<FriendInviteBio> loadAllSentPendingFriendInvitesForPlayer(PlayerId playerId) {
        List<FriendInviteJpaEntity> friendInviteJpaEntities = friendInviteJpaRepository.getAllByInviter_Id(playerId.uuid());
        if (friendInviteJpaEntities.isEmpty()) return null;

        return friendInviteJpaEntities.stream()
                .filter(invite -> invite.getStatus() == InviteStatus.PENDING) // Filter pending invites
                .map(invite -> {
                    Optional<PlayerJpaEntity> inviterEntity = playerJpaRepository.findById(invite.getInvited().getId());
                    return inviterEntity.map(player -> mapToFriendInviteBioDto(playerJpaToDomain(player), invite));
                })
                .filter(Optional::isPresent) // Remove empty results
                .map(Optional::get)
                .toList();
    }

    @Override
    @Transactional
    public boolean updateFriendInviteStatusToAccepted(FriendInviteId friendInviteId, PlayerId playerId) {
        FriendInviteJpaEntity friendInviteJpaEntity = friendInviteJpaRepository.getReferenceById(friendInviteId.uuid());
        FriendInvite friendInvite = this.friendInviteJpaToDomain(friendInviteJpaEntity);
        friendInvite.setStatusToAccepted();
        friendInviteJpaRepository.save(this.friendInviteDomainToJpa(friendInvite));

        PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity(friendInviteJpaEntity.getInvited().getId());
        PlayerJpaEntity friendJpaEntity = new PlayerJpaEntity(friendInviteJpaEntity.getInviter().getId());
        if (!playerJpaEntity.getId().equals(playerId.uuid())) throw new UnauthorizedPlayerException();

        PlayerFriendsJpaEntity newPlayerFriendsJpaEntity = new PlayerFriendsJpaEntity(
                UUID.randomUUID(),
                playerJpaEntity,
                friendJpaEntity
        );
        playerFriendJpaRepository.save(newPlayerFriendsJpaEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean updateFriendInviteStatusToRejected(FriendInviteId friendInviteId, PlayerId playerId) {
        FriendInviteJpaEntity friendInviteJpaEntity = friendInviteJpaRepository.getReferenceById(friendInviteId.uuid());
        FriendInvite friendInvite = this.friendInviteJpaToDomain(friendInviteJpaEntity);
        PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity(friendInviteJpaEntity.getInvited().getId());
        if (!playerJpaEntity.getId().equals(playerId.uuid())) throw new UnauthorizedPlayerException();

        friendInvite.setStatusToRejected();
        friendInviteJpaRepository.save(this.friendInviteDomainToJpa(friendInvite));
        return true;
    }

    private FriendInvite friendInviteJpaToDomain(FriendInviteJpaEntity friendInviteJpaEntity){
       return new FriendInvite(
                new FriendInviteId(friendInviteJpaEntity.getId()),
                new PlayerId(friendInviteJpaEntity.getInviter().getId()),
                new PlayerId(friendInviteJpaEntity.getInvited().getId()),
                friendInviteJpaEntity.getStatus(),
                friendInviteJpaEntity.getInvitedTime()
        );
    }

    private FriendInviteJpaEntity friendInviteDomainToJpa(FriendInvite friendInvite){
        PlayerJpaEntity inviter = playerJpaRepository.getReferenceById(friendInvite.getInviter().uuid());
        PlayerJpaEntity invited = playerJpaRepository.getReferenceById(friendInvite.getInvited().uuid());
        return new FriendInviteJpaEntity(
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

    private FriendInviteBio mapToFriendInviteBioDto(Player player, FriendInviteJpaEntity invite) {
        return new FriendInviteBio(
                player.getId().uuid().toString(),
                invite.getId(),
                player.getDisplayName(),
                player.getAvatar().url().url(),
                invite.getInvitedTime(),
                InviteStatus.PENDING
        );
    }
}
