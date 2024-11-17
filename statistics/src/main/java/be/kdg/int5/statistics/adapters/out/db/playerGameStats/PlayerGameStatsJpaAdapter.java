package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsUpdatePort;
import org.slf4j.LoggerFactory;

public class PlayerGameStatsJpaAdapter implements PlayerGameStatisticsUpdatePort {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlayerGameStatsJpaAdapter.class);
    private final PlayerGameStatsJpaRepository playerGameStatsJpaRepository;
    private final CompletedSessionJpaRepository completedSessionJpaRepository;

    public PlayerGameStatsJpaAdapter(
            final PlayerGameStatsJpaRepository playerGameStatsJpaRepository,
            final CompletedSessionJpaRepository completedSessionJpaRepository
    ) {
        this.playerGameStatsJpaRepository = playerGameStatsJpaRepository;
        this.completedSessionJpaRepository = completedSessionJpaRepository;
    }

    @Override
    public PlayerGameStats updatePlayerGameStat(PlayerGameStats playerGameStats) {
        PlayerGameStatsJpaId playerGameStatsJpaId = new PlayerGameStatsJpaId(playerGameStats.getPlayerId().uuid(), playerGameStats.getGameId().uuid());
        PlayerGameStatsJpaEntity playerGameStatsJpaEntity = playerGameStatsJpaRepository.findById(playerGameStatsJpaId).orElseThrow();

        return null;
    }
}
