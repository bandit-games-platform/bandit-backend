package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.adapters.in.dto.AchievementDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.RegisterGameDto;
import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.DeveloperId;
import be.kdg.int5.gameRegistry.domain.GameId;
import be.kdg.int5.gameRegistry.port.in.AuthenticateSDKCommand;
import be.kdg.int5.gameRegistry.port.in.AuthenticateSDKUseCase;
import be.kdg.int5.gameRegistry.port.in.RegisterGameCommand;
import be.kdg.int5.gameRegistry.port.in.RegisterGameUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registry")
public class GameRegistryRestController {
    private final AuthenticateSDKUseCase authenticateSDKUseCase;
    private final RegisterGameUseCase registerGameUseCase;

    public GameRegistryRestController(AuthenticateSDKUseCase authenticateSDKUseCase, RegisterGameUseCase registerGameUseCase) {
        this.authenticateSDKUseCase = authenticateSDKUseCase;
        this.registerGameUseCase = registerGameUseCase;
    }

    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateSDK(@RequestBody AuthenticateSDKCommand command) {
        try {
            return new ResponseEntity<>(authenticateSDKUseCase.authenticate(command), HttpStatus.OK);
        } catch (AuthenticateSDKUseCase.InvalidApiKeyException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (AuthenticateSDKUseCase.ImpersonationFailedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/games")
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity<GameId> registerGame(@AuthenticationPrincipal Jwt token, @RequestBody @Valid RegisterGameDto dto) {
        DeveloperId developerId = new DeveloperId(UUID.fromString(token.getSubject()));

        Set<Achievement> achievements = null;
        if (dto.getAchievements() != null) {
            achievements = dto
                    .getAchievements()
                    .stream()
                    .map(AchievementDto::mapToObject)
                    .collect(Collectors.toSet());
        }
        List<ImageResource> screenshots = null;
        if (dto.getScreenshots() != null) {
            screenshots = dto
                    .getScreenshots()
                    .stream()
                    .map(url -> new ImageResource(new ResourceURL(url)))
                    .collect(Collectors.toList());
        }

        GameId result = registerGameUseCase.registerGame(new RegisterGameCommand(
                developerId,
                dto.getTitle(),
                new ResourceURL(dto.getCurrentHost()),
                dto.getDescription(),
                dto.getCurrentPrice(),
                dto.getIconUrl(),
                dto.getBackgroundUrl(),
                dto.getRules(),
                achievements,
                screenshots
        ));

        if (result != null) {
            return ResponseEntity.ok(result);
        }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
