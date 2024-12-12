package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.GameInLibraryDto;
import be.kdg.int5.player.domain.GameId;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.domain.PlayerLibrary;
import be.kdg.int5.player.port.in.FavoriteGameCommand;
import be.kdg.int5.player.port.in.FavoriteGameUseCase;
import be.kdg.int5.player.port.in.query.PlayerLibraryQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players/library")
public class PlayerLibraryRestController {
    private final PlayerLibraryQuery playerLibraryQuery;
    private final FavoriteGameUseCase favoriteGameUseCase;

    public PlayerLibraryRestController(
            final PlayerLibraryQuery playerLibraryQuery,
            final FavoriteGameUseCase favoriteGameUseCase
    ) {
        this.playerLibraryQuery = playerLibraryQuery;
        this.favoriteGameUseCase = favoriteGameUseCase;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<GameInLibraryDto>> getAllGamesInPlayerLibrary(@AuthenticationPrincipal Jwt token) {
        PlayerLibrary playerLibrary = playerLibraryQuery.getPlayerLibrary(
                new PlayerId(UUID.fromString(token.getClaimAsString("sub")))
        );

        if (playerLibrary == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<GameInLibraryDto> gameInLibraryDtoList = playerLibrary.getPlayerLibraryItems().stream()
                .map(playerLibraryItem -> new GameInLibraryDto(
                        playerLibraryItem.getGameId().uuid(),
                        playerLibraryItem.isFavourite(),
                        playerLibraryItem.isHidden()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(gameInLibraryDtoList);
    }

    @PatchMapping("{gameId}/favourites")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<Void> updateGameFavoriteStatus(
            @PathVariable UUID gameId,
            @RequestBody Map<String, Boolean> requestBody,
            @AuthenticationPrincipal Jwt token
    ){
        String userId = token.getClaimAsString("sub");
        UUID playerId = UUID.fromString(userId);
        Boolean newFavoriteStatus = requestBody.get("favourite");
        if (newFavoriteStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        FavoriteGameCommand favoriteGameCommand = new FavoriteGameCommand(new PlayerId(playerId), new GameId(gameId), newFavoriteStatus);
        favoriteGameUseCase.changeGameFavoriteStatus(favoriteGameCommand);
        return ResponseEntity.noContent().build();
    }
}