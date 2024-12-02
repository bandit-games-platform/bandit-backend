package be.kdg.int5.gameplay.adapters.in;

import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.port.in.CreateLobbyCommand;
import be.kdg.int5.gameplay.port.in.CreateLobbyUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lobby")
public class LobbyRestController {
    private final CreateLobbyUseCase createLobbyUseCase;

    public LobbyRestController(CreateLobbyUseCase createLobbyUseCase) {
        this.createLobbyUseCase = createLobbyUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity<LobbyId> createLobby(@RequestBody CreateLobbyCommand command) {
        LobbyId result = createLobbyUseCase.create(command);

        if (result == null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
