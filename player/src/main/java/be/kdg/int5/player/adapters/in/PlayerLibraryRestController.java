package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.GameInLibraryDto;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.domain.PlayerLibrary;
import be.kdg.int5.player.port.in.query.PlayerLibraryQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PlayerLibraryRestController {
    private final PlayerLibraryQuery playerLibraryQuery;

    public PlayerLibraryRestController(PlayerLibraryQuery playerLibraryQuery) {
        this.playerLibraryQuery = playerLibraryQuery;
    }

    @GetMapping("/players/library")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<GameInLibraryDto>> getAllGamesInPlayerLibrary(@AuthenticationPrincipal Jwt token) {
        PlayerLibrary playerLibrary = playerLibraryQuery.getPlayerLibrary(
                new PlayerId(UUID.fromString(token.getClaimAsString("sub")))
        );

        if (playerLibrary == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<GameInLibraryDto> gameInLibraryDtoList = playerLibrary.getPlayerLibraryItems().stream()
                .map(playerLibraryItem -> new GameInLibraryDto(
                        playerLibraryItem.getGameId().uuid(),
                        playerLibraryItem.isFavourite(), playerLibraryItem.isHidden()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(gameInLibraryDtoList);
    }
}
