package be.kdg.int5.gameRegistry.adapters.out.db.achievement;

import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.AchievementId;
import be.kdg.int5.gameRegistry.domain.GameId;
import be.kdg.int5.gameRegistry.port.out.AchievementsLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class AchievementJpaAdapter implements AchievementsLoadPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(AchievementJpaAdapter.class);

    private final AchievementJpaRepository achievementJpaRepository;

    public AchievementJpaAdapter(AchievementJpaRepository achievementJpaRepository) {
        this.achievementJpaRepository = achievementJpaRepository;
    }


    @Override
    public List<Achievement> loadGameAchievements(GameId gameId) {
        List<AchievementJpaEntity> achievements = achievementJpaRepository
                .findAllByGameId(gameId.uuid());

        return achievements.stream()
                .map(this::achievementJpaToDomain)
                .toList();
    }

    private Achievement achievementJpaToDomain(final AchievementJpaEntity achievementJpaEntity){
        return new Achievement(
                new AchievementId(achievementJpaEntity.getId()),
                achievementJpaEntity.getTitle(),
                achievementJpaEntity.getCounterTotal(),
                achievementJpaEntity.getDescription()
        );
    }
}
