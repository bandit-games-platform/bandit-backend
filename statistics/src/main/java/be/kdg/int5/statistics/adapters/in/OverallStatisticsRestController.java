package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.query.PlayerTotalPlayTimeCommand;
import be.kdg.int5.statistics.port.in.query.PlayerTotalPlayTimeQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/statistics")
public class OverallStatisticsRestController {
    private final PlayerTotalPlayTimeQuery playerTotalPlayTimeQuery;

    public OverallStatisticsRestController(PlayerTotalPlayTimeQuery playerTotalPlayTimeQuery) {
        this.playerTotalPlayTimeQuery = playerTotalPlayTimeQuery;
    }

    @GetMapping("/playtime")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<Long> getTotalPlaytime(
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        long playTime = playerTotalPlayTimeQuery.getTotalPlayTimeSecondsForPlayer(new PlayerTotalPlayTimeCommand(
                new PlayerId(playerId)
        ));
        if (playTime == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(playTime);
    }
}
