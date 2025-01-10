package be.kdg.int5.gameplay.adapters.in;

import be.kdg.int5.gameplay.adapters.in.dto.GetGameInviteDto;
import be.kdg.int5.gameplay.adapters.in.dto.LobbyJoinInfoDto;
import be.kdg.int5.gameplay.adapters.in.dto.NewGameInviteDto;
import be.kdg.int5.gameplay.domain.*;
import be.kdg.int5.gameplay.port.in.*;
import be.kdg.int5.gameplay.port.in.query.PendingGameInvitesQuery;
import be.kdg.int5.gameplay.port.in.query.PlayerCanInviteToLobbyQuery;
import be.kdg.int5.gameplay.port.in.query.TitleForGameIdQuery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lobby/invite")
public class GameInviteRestController {
    private final PendingGameInvitesQuery pendingGameInvitesQuery;
    private final TitleForGameIdQuery titleForGameIdQuery;
    private final AcceptInviteUseCase acceptInviteUseCase;
    private final DismissInviteUseCase dismissInviteUseCase;
    private final PlayerCanInviteToLobbyQuery playerCanInviteToLobbyQuery;
    private final CreateGameInviteUseCase createGameInviteUseCase;

    public GameInviteRestController(
            PendingGameInvitesQuery pendingGameInvitesQuery,
            TitleForGameIdQuery titleForGameIdQuery,
            AcceptInviteUseCase acceptInviteUseCase,
            DismissInviteUseCase dismissInviteUseCase,
            PlayerCanInviteToLobbyQuery playerCanInviteToLobbyQuery,
            CreateGameInviteUseCase createGameInviteUseCase
    ) {
        this.pendingGameInvitesQuery = pendingGameInvitesQuery;
        this.titleForGameIdQuery = titleForGameIdQuery;
        this.acceptInviteUseCase = acceptInviteUseCase;
        this.dismissInviteUseCase = dismissInviteUseCase;
        this.playerCanInviteToLobbyQuery = playerCanInviteToLobbyQuery;
        this.createGameInviteUseCase = createGameInviteUseCase;
    }

    @GetMapping("/can-invite/{gameId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<UUID> canPlayerCurrentlyInviteToLobby(@AuthenticationPrincipal Jwt token, @PathVariable UUID gameId) {
        PlayerId requestingPlayerThatWouldBeLobbyOwner = new PlayerId(UUID.fromString(token.getSubject()));
        GameId forGame = new GameId(gameId);

        LobbyId lobbyIdIfPlayerCanInvite = playerCanInviteToLobbyQuery.canPlayerCurrentlyInviteOthersToLobby(
                requestingPlayerThatWouldBeLobbyOwner,
                forGame
        );

        if (lobbyIdIfPlayerCanInvite == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(lobbyIdIfPlayerCanInvite.uuid());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<GetGameInviteDto>> getPendingInvites(@AuthenticationPrincipal Jwt token) {
        PlayerId invitedPlayer = new PlayerId(UUID.fromString(token.getSubject()));
        List<GameInvite> invitesForPlayer = pendingGameInvitesQuery.getAllPendingGameInvitesForPlayer(invitedPlayer);

        if (invitesForPlayer == null || invitesForPlayer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<GetGameInviteDto> inviteDtos = invitesForPlayer
                .stream()
                .map(this::mapGameInviteDomainToDto)
                .toList();

        fillGameTitleForInvites(inviteDtos);

        return ResponseEntity.ok(inviteDtos);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<Void> createNewInvite(@AuthenticationPrincipal Jwt token, @RequestBody @Valid NewGameInviteDto newGameInviteDto) {
        PlayerId inviterId = new PlayerId(UUID.fromString(token.getSubject()));

        boolean result = createGameInviteUseCase.createInvite(new CreateGameInviteCommand(
                new LobbyId(newGameInviteDto.getLobbyId()),
                new PlayerId(newGameInviteDto.getInvitedId()),
                inviterId
        ));

        if (result) return new ResponseEntity<>(HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{inviteId}/dismiss")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<Void> dismissInvite(@AuthenticationPrincipal Jwt token, @PathVariable UUID inviteId) {
        PlayerId requestingPlayer = new PlayerId(UUID.fromString(token.getSubject()));
        GameInviteId gameInviteId = new GameInviteId(inviteId);

        boolean dismissed = dismissInviteUseCase.dismissInvite(new DismissInviteCommand(gameInviteId, requestingPlayer));

        if (dismissed) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/{inviteId}/accept")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<LobbyJoinInfoDto> acceptInviteAndGetJoinInfo(@AuthenticationPrincipal Jwt token, @PathVariable UUID inviteId) {
        PlayerId requestingPlayer = new PlayerId(UUID.fromString(token.getSubject()));
        GameInviteId gameInviteId = new GameInviteId(inviteId);

        try {
            LobbyJoinInfo lobbyJoinInfo = acceptInviteUseCase.acceptInviteAndGetJoinInfo(new AcceptInviteCommand(gameInviteId, requestingPlayer));

            return ResponseEntity.ok(new LobbyJoinInfoDto(
                    lobbyJoinInfo.lobbyId().uuid(),
                    lobbyJoinInfo.gameId().uuid()
            ));
        } catch (AcceptInviteUseCase.NoSuchInviteException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (AcceptInviteUseCase.LobbyClosedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    private void fillGameTitleForInvites(List<GetGameInviteDto> inviteDtos) {
        // avoid duplicate queries
        Map<UUID, String> cache = new HashMap<>();

        for (GetGameInviteDto dto : inviteDtos) {
            String title = cache.get(dto.getGameId());
            if (title == null) {
                title = titleForGameIdQuery.getGameTitleForGameIdInProjection(new GameId(dto.getGameId()));
                cache.put(dto.getGameId(), title);
            }

            dto.setGameTitle(title);
        }
    }

    private GetGameInviteDto mapGameInviteDomainToDto(GameInvite domain) {
        return new GetGameInviteDto(
                domain.getId().uuid(),
                domain.getInviter().uuid(),
                domain.getLobby().getGameId().uuid()
        );
    }
}
