package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.gameRegistry.port.in.AuthenticateSDKCommand;
import be.kdg.int5.gameRegistry.port.in.AuthenticateSDKUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registry")
public class GameRegistryRestController {
    private final AuthenticateSDKUseCase authenticateSDKUseCase;

    public GameRegistryRestController(AuthenticateSDKUseCase authenticateSDKUseCase) {
        this.authenticateSDKUseCase = authenticateSDKUseCase;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authenticateSDK(@RequestBody AuthenticateSDKCommand command) {
        try {
            return new ResponseEntity<>(authenticateSDKUseCase.authenticate(command), HttpStatus.OK);
        } catch (AuthenticateSDKUseCase.InvalidApiKeyException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (AuthenticateSDKUseCase.ImpersonationFailedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
