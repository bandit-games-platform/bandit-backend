package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.NewPlayerRegistrationDto;
import be.kdg.int5.player.port.in.RegisterPlayerCommand;
import be.kdg.int5.player.port.in.RegisterPlayerUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/registration")
public class NewRegistrationController {
    private static final String authorizationId = "4b0f0827-3951-4839-85de-7e4335069389";
    private static final Logger LOGGER = LoggerFactory.getLogger(NewRegistrationController.class);
    private final RegisterPlayerUseCase registerPlayerUseCase;

    public NewRegistrationController(RegisterPlayerUseCase registerPlayerUseCase) {
        this.registerPlayerUseCase = registerPlayerUseCase;
    }

    @PostMapping("/{authenticationId}")
    public void registerNewRegistration(@PathVariable String authenticationId, @Valid @RequestBody NewPlayerRegistrationDto dto) {
        UUID identifyingId = UUID.nameUUIDFromBytes((authorizationId + "-" + dto.getUserId() + "-" + dto.getUsername()).getBytes());

        if (!Objects.equals(authenticationId, identifyingId.toString())) return;

        LOGGER.info("New player registration with id {} and username {}", dto.getUserId(), dto.getUsername());

        registerPlayerUseCase.registerOrUpdatePlayerAccount(new RegisterPlayerCommand(UUID.fromString(dto.getUserId()), dto.getUsername()));
    }
}
