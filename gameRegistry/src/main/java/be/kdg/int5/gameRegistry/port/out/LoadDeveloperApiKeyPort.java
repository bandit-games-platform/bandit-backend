package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.DeveloperApiKey;

import java.util.Optional;

public interface LoadDeveloperApiKeyPort {
    Optional<DeveloperApiKey> load(String apiKey);
}
