package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompletedSessionJpaRepository extends JpaRepository<CompletedSessionJpaEntity, UUID> {
}
