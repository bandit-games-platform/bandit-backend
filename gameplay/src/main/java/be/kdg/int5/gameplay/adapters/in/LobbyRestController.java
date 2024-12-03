package be.kdg.int5.gameplay.adapters.in;

import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import be.kdg.int5.gameplay.port.in.PatchLobbyCommand;
import be.kdg.int5.gameplay.port.in.PatchLobbyUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lobby")
public class LobbyRestController {
    private final CreateLobbyUseCase createLobbyUseCase;
    private final PatchLobbyUseCase patchLobbyUseCase;

    public LobbyRestController(CreateLobbyUseCase createLobbyUseCase, PatchLobbyUseCase patchLobbyUseCase) {
        this.createLobbyUseCase = createLobbyUseCase;
        this.patchLobbyUseCase = patchLobbyUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity<LobbyId> createLobby(@RequestBody CreateLobbyCommand command) {
        LobbyId result = createLobbyUseCase.create(command);

        if (result == null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity patchLobby(@RequestBody PatchLobbyCommand command) {
        HttpStatus response = patchLobbyUseCase.patch(command) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(response);
    }
}
