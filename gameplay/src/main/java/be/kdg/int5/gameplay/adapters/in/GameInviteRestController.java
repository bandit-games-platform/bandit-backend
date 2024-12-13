package be.kdg.int5.gameplay.adapters.in;

import be.kdg.int5.gameplay.adapters.in.dto.GetGameInviteDto;
import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.GameInvite;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.query.PendingGameInvitesQuery;
import be.kdg.int5.gameplay.port.in.query.TitleForGameIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lobby/invite")
public class GameInviteRestController {
    private final PendingGameInvitesQuery pendingGameInvitesQuery;
    private final TitleForGameIdQuery titleForGameIdQuery;

    public GameInviteRestController(PendingGameInvitesQuery pendingGameInvitesQuery, TitleForGameIdQuery titleForGameIdQuery) {
        this.pendingGameInvitesQuery = pendingGameInvitesQuery;
        this.titleForGameIdQuery = titleForGameIdQuery;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    private ResponseEntity<List<GetGameInviteDto>> getPendingInvites(@AuthenticationPrincipal Jwt token) {
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
