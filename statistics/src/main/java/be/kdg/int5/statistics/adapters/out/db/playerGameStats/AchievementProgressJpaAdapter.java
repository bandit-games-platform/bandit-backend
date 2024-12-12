package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.AchievementId;
import be.kdg.int5.statistics.domain.AchievementProgress;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.port.out.AchievementProgressForGameLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AchievementProgressJpaAdapter implements AchievementProgressForGameLoadPort {
    private final AchievementProgressJpaRepository achievementProgressJpaRepository;

    public AchievementProgressJpaAdapter(AchievementProgressJpaRepository achievementProgressJpaRepository) {
        this.achievementProgressJpaRepository = achievementProgressJpaRepository;
    }

    @Override
    public List<AchievementProgress> loadAllAchievementProgressForGame(GameId gameId) {
        List<AchievementProgressJpaEntity> allAchievementProgress =  achievementProgressJpaRepository.findAchievementProgressByGameId(gameId.uuid());
        return allAchievementProgress.stream().map(this::achievementProgressJpaToDomain).collect(Collectors.toList());

    }

    private AchievementProgress achievementProgressJpaToDomain(final AchievementProgressJpaEntity achievementProgressJpaEntity){
        return new AchievementProgress(
                new AchievementId(achievementProgressJpaEntity.getAchievementId()),
                achievementProgressJpaEntity.getCounterTotal()
        );
    }
}
