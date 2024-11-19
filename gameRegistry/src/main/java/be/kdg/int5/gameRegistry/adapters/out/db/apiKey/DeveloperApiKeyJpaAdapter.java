package be.kdg.int5.gameRegistry.adapters.out.db.apiKey;

import be.kdg.int5.gameRegistry.domain.DeveloperApiKey;
import be.kdg.int5.gameRegistry.domain.DeveloperId;
import be.kdg.int5.gameRegistry.port.out.LoadDeveloperApiKeyPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DeveloperApiKeyJpaAdapter implements LoadDeveloperApiKeyPort {
    private final DeveloperApiKeyJpaRepository repository;

    public DeveloperApiKeyJpaAdapter(DeveloperApiKeyJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DeveloperApiKey> load(String apiKey) {
        return repository.findById(apiKey).map(this::mapEntityToDomain);
    }

    private DeveloperApiKey mapEntityToDomain(DeveloperApiKeyJpaEntity entity) {
        return new DeveloperApiKey(
                new DeveloperId(entity.getDeveloperId()),
                entity.getApiKey(),
                entity.isRevoked()
        );
    }
}
