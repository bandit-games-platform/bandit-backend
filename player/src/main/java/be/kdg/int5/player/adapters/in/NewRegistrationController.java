package be.kdg.int5.player.adapters.in;

import be.kdg.int5.player.adapters.in.dto.NewPlayerRegistrationDto;
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

    @PostMapping("/{authenticationId}")
    public void registerNewRegistration(@PathVariable String authenticationId, @RequestBody NewPlayerRegistrationDto dto) {
        UUID identifyingId = UUID.nameUUIDFromBytes((authorizationId + "-" + dto.getUserId() + "-" + dto.getUsername()).getBytes());

        if (!Objects.equals(authenticationId, identifyingId.toString())) return;

        LOGGER.info("New player registration with id {} and username {}", dto.getUserId(), dto.getUsername());
    }
}
