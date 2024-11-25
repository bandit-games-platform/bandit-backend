package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompletedSessionJpaRepository extends JpaRepository<CompletedSessionJpaEntity, UUID> {
}
