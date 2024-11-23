package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.adapters.out.db.playerGameStats.PlayerGameStatsJpaAdapter;
import be.kdg.int5.statistics.domain.AchievementProgress;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.UpdateAchievementProgressCommand;
import be.kdg.int5.statistics.port.in.UpdateAchievementProgressUseCase;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsUpdatePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UpdateAchievementProgressUseCaseImpl implements UpdateAchievementProgressUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateAchievementProgressUseCaseImpl.class);
    private final PlayerGameStatisticsUpdatePort playerGameStatisticsUpdatePort;
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;

    public UpdateAchievementProgressUseCaseImpl(PlayerGameStatisticsUpdatePort playerGameStatisticsUpdatePort, PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort) {
        this.playerGameStatisticsUpdatePort = playerGameStatisticsUpdatePort;
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
    }

    @Override
    @Transactional
    public void updateAchievementProgress(UpdateAchievementProgressCommand command) {
        PlayerGameStats playerGameStats;

        try {
            playerGameStats = playerGameStatisticsLoadPort.loadPlayerGameStatsWithAllRelationships(command.playerId(), command.gameId());
            logger.info("statistics: Existing player achievement progress already found for game {} and player {}", command.gameId().uuid(), command.playerId().uuid());

            Set<AchievementProgress> achievementProgressSet = playerGameStats.getAchievementProgressSet();
            boolean foundForGame = false;
            for (AchievementProgress achievementProgress : achievementProgressSet) {
                if (achievementProgress.getAchievementId().uuid().equals(command.achievementId().uuid())) {
                    achievementProgressSet.remove(achievementProgress);
                    achievementProgress.setCounterValue(
                            command.newAmount() != null ?
                                    command.newAmount() :
                                    achievementProgress.getCounterValue() + 1);
                    achievementProgressSet.add(achievementProgress);
                    foundForGame = true;
                    break;
                }
            }
            if (achievementProgressSet.isEmpty() || !foundForGame) {
                AchievementProgress achievementProgress = new AchievementProgress(
                        command.achievementId(),
                        command.newAmount() != null ? command.newAmount() : 1
                );
                playerGameStats.addAchievementProgress(achievementProgress);
            }
        } catch (NoSuchElementException e) {
            AchievementProgress achievementProgress = new AchievementProgress(
                    command.achievementId(),
                    command.newAmount() != null ? command.newAmount() : 1
            );
            playerGameStats = new PlayerGameStats(command.playerId(), command.gameId(), achievementProgress);
            logger.info("statistics: New player achievement progress created for game {} and player {}", command.gameId().uuid(), command.playerId().uuid());
        }

        playerGameStatisticsUpdatePort.updateAchievementProgress(playerGameStats);
    }
}
