package be.kdg.int5.gameRegistry.port.in;

import java.util.Objects;

public record AuthenticateSDKCommand(String apiKey) {

    public AuthenticateSDKCommand {
        Objects.requireNonNull(apiKey);
    }
}
