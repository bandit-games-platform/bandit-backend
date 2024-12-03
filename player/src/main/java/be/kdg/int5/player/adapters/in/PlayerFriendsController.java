package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.FriendInviteBioDto;
import be.kdg.int5.player.adapters.in.dto.PlayerFriendBioDto;
import be.kdg.int5.player.adapters.in.dto.PlayerSearchBioDto;
import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.ProcessPendingFriendInvite;
import be.kdg.int5.player.port.in.*;
import be.kdg.int5.player.port.in.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/player")
public class    PlayerFriendsController {
    private final SearchForNewFriendsUseCase searchForNewFriendsUseCase;
    private final FriendsListQuery friendsListQuery;
    private final SendFriendInviteUseCase sendFriendInviteUseCase;
    private final PendingFriendInvitesQuery pendingFriendInvitesQuery;
    private final ProcessPendingFriendInvite processPendingFriendInvite;

    public PlayerFriendsController(SearchForNewFriendsUseCase searchForNewFriendsUseCase, FriendsListQuery friendsListQuery, SendFriendInviteUseCase sendFriendInviteUseCase, PendingFriendInvitesQuery pendingFriendInvitesQuery, ProcessPendingFriendInvite processPendingFriendInvite) {
        this.searchForNewFriendsUseCase = searchForNewFriendsUseCase;
        this.friendsListQuery = friendsListQuery;
        this.sendFriendInviteUseCase = sendFriendInviteUseCase;
        this.pendingFriendInvitesQuery = pendingFriendInvitesQuery;
        this.processPendingFriendInvite = processPendingFriendInvite;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('player')")
    ResponseEntity<List<PlayerSearchBioDto>> searchForNewFriend(@RequestParam String username, @AuthenticationPrincipal Jwt token){
        String userId = token.getClaimAsString("sub");
        SearchForNewFriendsCommand command = new SearchForNewFriendsCommand(username, new PlayerId(UUID.fromString(userId)));
        List<Player> playersList = searchForNewFriendsUseCase.searchForNewFriends(command);
        if (playersList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<PlayerSearchBioDto> newPlayersList = playersList.stream()
                .map(this::mapToSearchDto)
                .toList();
        return new ResponseEntity<>(newPlayersList, HttpStatus.OK);
    }

    @GetMapping("/friends")
    @PreAuthorize("hasAuthority('player')")
    ResponseEntity<List<PlayerFriendBioDto>> getAllPlayerFriends(@AuthenticationPrincipal Jwt token){
        String userId = token.getClaimAsString("sub");
        UUID playerId = UUID.fromString(userId);
        List<Player> playersList = friendsListQuery.getFriendsList(new GetFriendsListCommand(new PlayerId(playerId)));
        if (playersList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<PlayerFriendBioDto> newPlayersList = playersList.stream()
                .map(this::mapFriendToDto)
                .toList();
        return new ResponseEntity<>(newPlayersList, HttpStatus.OK);
    }

    @PostMapping("/invite-new-friends/{friendId}")
    @PreAuthorize("hasAuthority('player')")
    ResponseEntity<Boolean> sendNewFriendInvite(@PathVariable UUID friendId, @AuthenticationPrincipal Jwt token){
        String userId = token.getClaimAsString("sub");
        UUID playerId = UUID.fromString(userId);
        SendFriendInviteCommand command = new SendFriendInviteCommand(new PlayerId(playerId), new PlayerId(friendId));
        if (sendFriendInviteUseCase.sendFriendInvite(command)){
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    @GetMapping("/friends/pending-invites/received")
    @PreAuthorize("hasAuthority('player')")
    ResponseEntity<List<FriendInviteBioDto>> getAllPendingReceivedFriendInvites(@AuthenticationPrincipal Jwt token){
        String userId = token.getClaimAsString("sub");
        UUID playerId = UUID.fromString(userId);
        GetPendingReceivedFriendInvitesCommand command = new GetPendingReceivedFriendInvitesCommand(new PlayerId(playerId));
        List<FriendInviteBioDto> friendInviteList = pendingFriendInvitesQuery.getAllReceivedPendingFriendInvites(command);
        return new ResponseEntity<>(friendInviteList, HttpStatus.OK);
    }

    @GetMapping("/friends/pending-invites/sent")
    @PreAuthorize("hasAuthority('player')")
    ResponseEntity<List<FriendInviteBioDto>> getAllPendingSentFriendInvites(@AuthenticationPrincipal Jwt token){
        String userId = token.getClaimAsString("sub");
        UUID playerId = UUID.fromString(userId);
        GetPendingSentFriendInvitesCommand command = new GetPendingSentFriendInvitesCommand(new PlayerId(playerId));
        List<FriendInviteBioDto> friendInviteList = pendingFriendInvitesQuery.getAllSentPendingFriendInvites(command);
        return new ResponseEntity<>(friendInviteList, HttpStatus.OK);
    }

    @PostMapping("/friends/pending-invites/{friendInviteId}")
    @PreAuthorize("hasAuthority('player')")
    ResponseEntity<Void> processFriendInvite(
            @PathVariable UUID friendInviteId,
            @RequestParam("action") String action,
            @AuthenticationPrincipal Jwt token) {

        String userId = token.getClaimAsString("sub");
        UUID playerId = UUID.fromString(userId);
        ProcessPendingFriendInviteCommand command = new ProcessPendingFriendInviteCommand(
                new FriendInviteId(friendInviteId),
                new PlayerId(playerId)
        );

        if ("accept".equalsIgnoreCase(action)) {
            if (processPendingFriendInvite.acceptPendingFriendInvite(command)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if ("reject".equalsIgnoreCase(action)) {
            if (processPendingFriendInvite.rejectPendingFriendInvite(command)){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    private PlayerFriendBioDto mapFriendToDto(Player player){
        return new PlayerFriendBioDto(player.getId().uuid().toString(), player.getDisplayName(), player.getAvatar().url().url(), true);
    }

    private PlayerSearchBioDto mapToSearchDto(Player player){
        return new PlayerSearchBioDto(player.getId().uuid().toString(), player.getDisplayName(), player.getAvatar().url().url(), false);
    }
}