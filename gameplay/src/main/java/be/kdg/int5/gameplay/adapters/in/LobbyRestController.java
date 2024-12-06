package be.kdg.int5.gameplay.adapters.in;

import be.kdg.int5.gameplay.domain.DeveloperId;
import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import be.kdg.int5.gameplay.port.in.PatchLobbyCommand;
import be.kdg.int5.gameplay.port.in.PatchLobbyUseCase;
import be.kdg.int5.gameplay.port.in.query.VerifyDeveloperOwnsGameQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lobby")
public class LobbyRestController {
    private final CreateLobbyUseCase createLobbyUseCase;
    private final PatchLobbyUseCase patchLobbyUseCase;
    private final VerifyDeveloperOwnsGameQuery verifyDeveloperOwnsGameQuery;

    public LobbyRestController(CreateLobbyUseCase createLobbyUseCase, PatchLobbyUseCase patchLobbyUseCase, VerifyDeveloperOwnsGameQuery verifyDeveloperOwnsGameQuery) {
        this.createLobbyUseCase = createLobbyUseCase;
        this.patchLobbyUseCase = patchLobbyUseCase;
        this.verifyDeveloperOwnsGameQuery = verifyDeveloperOwnsGameQuery;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity<LobbyId> createLobby(@RequestBody CreateLobbyCommand command, @AuthenticationPrincipal Jwt token) {
        DeveloperId developerId = new DeveloperId(UUID.fromString(token.getSubject()));
        if (!verifyDeveloperOwnsGameQuery.verify(developerId, new GameId(command.gameId()))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LobbyId result = createLobbyUseCase.create(command);

        if (result == null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity patchLobby(@RequestBody PatchLobbyCommand command, @AuthenticationPrincipal Jwt token) {
        DeveloperId developerId = new DeveloperId(UUID.fromString(token.getSubject()));
        if (!verifyDeveloperOwnsGameQuery.verifyByLobbyId(developerId, new LobbyId(command.lobbyId()))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(patchLobbyUseCase.patch(command)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
