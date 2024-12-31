package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.LoadPlayerBioDto;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.query.GetPlayerBioCommand;
import be.kdg.int5.player.port.in.query.GetPlayerJoinDateCommand;
import be.kdg.int5.player.port.in.query.GetPlayerJoinDateQuery;
import be.kdg.int5.player.port.in.query.PlayerBioQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class PlayerDetailsRestController {
    private final GetPlayerJoinDateQuery getPlayerJoinDateQuery;

    public PlayerDetailsRestController(GetPlayerJoinDateQuery getPlayerJoinDateQuery) {
        this.getPlayerJoinDateQuery = getPlayerJoinDateQuery;
    }

    @GetMapping("/players/join-date")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<LocalDateTime> getPlayerJoinDate(
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        LocalDateTime joinDate = getPlayerJoinDateQuery.getPlayerJoinDate(new GetPlayerJoinDateCommand(
                new PlayerId(playerId)
        ));
        if (joinDate == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(joinDate);
    }
}
