package be.kdg.int5.gameRegistry.adapters.out.db;

import be.kdg.int5.gameRegistry.adapters.out.db.game.DeveloperJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.game.DeveloperJpaRepository;
import be.kdg.int5.gameRegistry.domain.Developer;
import be.kdg.int5.gameRegistry.domain.DeveloperId;
import be.kdg.int5.gameRegistry.port.out.DeveloperLoadPort;
import org.springframework.stereotype.Repository;

@Repository
public class DeveloperJpaAdapter implements DeveloperLoadPort {
    private final DeveloperJpaRepository repository;

    public DeveloperJpaAdapter(DeveloperJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Developer load(DeveloperId developerId) {
        return repository.findById(developerId.uuid()).map(this::mapEntityToDomain).orElse(null);
    }

    private Developer mapEntityToDomain(DeveloperJpaEntity entity) {
        return new Developer(
                new DeveloperId(entity.getId()),
                entity.getStudioName()
        );
    }
}
