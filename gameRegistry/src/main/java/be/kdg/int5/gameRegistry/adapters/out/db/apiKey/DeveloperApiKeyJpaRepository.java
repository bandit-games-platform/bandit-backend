package be.kdg.int5.gameRegistry.adapters.out.db.apiKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperApiKeyJpaRepository extends JpaRepository<DeveloperApiKeyJpaEntity, String> {
}
