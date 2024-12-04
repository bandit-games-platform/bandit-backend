package be.kdg.int5.gameplay.adapters.out.db;

import be.kdg.int5.gameplay.domain.DeveloperId;
import be.kdg.int5.gameplay.domain.GameDeveloperProjection;
import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.port.out.GameDeveloperProjectionLoadPort;
import be.kdg.int5.gameplay.port.out.GameDeveloperProjectionSavePort;
import org.springframework.stereotype.Repository;

@Repository
public class GameDeveloperProjectionJpaAdapter implements GameDeveloperProjectionSavePort, GameDeveloperProjectionLoadPort {
    private final GameDeveloperProjectionJpaRepository repository;

    public GameDeveloperProjectionJpaAdapter(GameDeveloperProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(GameDeveloperProjection gameDeveloperProjection) {
        repository.save(mapDomainToEntity(gameDeveloperProjection));
    }

    private GameDeveloperProjectionJpaEntity mapDomainToEntity(GameDeveloperProjection gameDeveloperProjection) {
        return new GameDeveloperProjectionJpaEntity(
                gameDeveloperProjection.gameId().uuid(),
                gameDeveloperProjection.developerId().uuid()
        );
    }

    @Override
    public GameDeveloperProjection loadByGameId(GameId gameId) {
        return repository.findById(gameId.uuid()).map(this::mapEntityToDomain).orElse(null);
    }

    private GameDeveloperProjection mapEntityToDomain(GameDeveloperProjectionJpaEntity jpa) {
        return new GameDeveloperProjection(
                new GameId(jpa.getGameId()),
                new DeveloperId(jpa.getDeveloperId())
        );
    }
}
