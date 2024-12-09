package be.kdg.int5.player.adapters.out.db.playerFriend;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.common.exceptions.FriendInviteNotFoundException;
import be.kdg.int5.common.exceptions.InviteStatusExistsException;
import be.kdg.int5.player.domain.FriendInviteBio;
import be.kdg.int5.player.adapters.out.db.player.ImageResourceJpaEmbed;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.*;
import be.kdg.int5.player.port.out.*;
import be.kdg.int5.player.port.out.friends.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerFriendsJpaAdapter implements PlayerUsernameLoadPort, FriendsListLoadPort, FriendInviteCreatePort, FriendInviteLoadPort, FriendInviteUpdatePort, FriendRelationCreatePort {
    private final PlayerJpaRepository playerJpaRepository;
    private final FriendInviteJpaRepository friendInviteJpaRepository;
    private final FriendsRelationJpaRepository friendsRelationJpaRepository;

    public PlayerFriendsJpaAdapter(final PlayerJpaRepository playerJpaRepository,
                                   final FriendInviteJpaRepository friendInviteJpaRepository,
                                   final FriendsRelationJpaRepository friendsRelationJpaRepository
    ) {
        this.playerJpaRepository = playerJpaRepository;
        this.friendInviteJpaRepository = friendInviteJpaRepository;
        this.friendsRelationJpaRepository = friendsRelationJpaRepository;
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
        List<UUID> playerJpaEntitiesUUID = friendsRelationJpaRepository.findAllByPlayerIdOrFriendId(playerId.uuid());
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

        FriendInviteJpaEntity oldFriendInviteJpaEntity = friendInviteJpaRepository.findByPlayers(inviter.getId(), invited.getId());
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
                .filter(invite -> invite.getStatus() == InviteStatus.PENDING)
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
    public FriendInvite loadFriendInviteById(FriendInviteId friendInviteId) {
        FriendInviteJpaEntity friendInviteJpaEntity = friendInviteJpaRepository.findById(friendInviteId.uuid()).orElseThrow(FriendInviteNotFoundException::new);
        return this.friendInviteJpaToDomain(friendInviteJpaEntity);
    }

    @Override
    public void updateFriendInvite(FriendInvite friendInvite) {
        FriendInviteJpaEntity friendInviteJpaEntity = this.friendInviteDomainToJpa(friendInvite);
        friendInviteJpaRepository.save(friendInviteJpaEntity);
    }

    @Override
    public void createFriendRelation(FriendRelation friendRelation) {
        friendsRelationJpaRepository.save(this.friendsRelationDomainToJpa(friendRelation));
    }

    private FriendsRelationJpaEntity friendsRelationDomainToJpa(FriendRelation friendRelation){
        return new FriendsRelationJpaEntity(
                friendRelation.getId(),
                this.playerIdDomainToJpa(friendRelation.getFriendA()),
                this.playerIdDomainToJpa(friendRelation.getFriendB()),
                friendRelation.getFriendshipStartDate()
        );
    }

    private FriendInvite friendInviteJpaToDomain(FriendInviteJpaEntity friendInviteJpaEntity){
       FriendInvite friendInvite = new FriendInvite(
                new FriendInviteId(friendInviteJpaEntity.getId()),
                new PlayerId(friendInviteJpaEntity.getInviter().getId()),
                new PlayerId(friendInviteJpaEntity.getInvited().getId()),
                friendInviteJpaEntity.getInvitedTime()
        );
       friendInvite.setStatus(friendInviteJpaEntity.getStatus());
       return friendInvite;
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

    private PlayerJpaEntity playerIdDomainToJpa(Player player) {
        return new PlayerJpaEntity(player.getId().uuid());
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
