package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.LoadPlayerBioDto;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.query.GetPlayerBioCommand;
import be.kdg.int5.player.port.in.query.PlayerBioQuery;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PlayerBioRestController {
    private final PlayerBioQuery playerBioQuery;

    public PlayerBioRestController(PlayerBioQuery playerBioQuery) {
        this.playerBioQuery = playerBioQuery;
    }

    @GetMapping("/players/{playerId}")
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<LoadPlayerBioDto> getPlayerBio(
            @NotNull @PathVariable UUID playerId
    ) {
        Player player = playerBioQuery.getPlayerBio(
                new GetPlayerBioCommand(new PlayerId(playerId)));

        if (player == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LoadPlayerBioDto dto = new LoadPlayerBioDto(
                player.getId().uuid().toString(),
                player.getDisplayName(),
                player.getAvatar().toString()
        );

        return ResponseEntity.ok(dto);
    }
}
