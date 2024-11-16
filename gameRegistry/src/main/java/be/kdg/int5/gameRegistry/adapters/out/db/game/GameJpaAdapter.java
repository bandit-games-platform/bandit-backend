package be.kdg.int5.gameRegistry.adapters.out.db.game;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.adapters.out.db.achievement.AchievementJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.rule.RuleJpaEntity;
import be.kdg.int5.gameRegistry.domain.*;
import be.kdg.int5.gameRegistry.port.out.LoadGamesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GameJpaAdapter implements LoadGamesPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameJpaAdapter.class);

    private final GameJpaRepository gameJpaRepository;

    public GameJpaAdapter(GameJpaRepository gameJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
    }

    @Override
    public Game loadGameByIdWithDetails(UUID gameId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByIdWithAllRelationships(gameId);

        if (gameJpaEntity == null) return null;
        Game game = toGame(gameJpaEntity);
        LOGGER.info("Loaded game: {}", game.getTitle());
        return game;
    }

    @Override
    public List<Game> loadAllGamesWithIcon() {
        return gameJpaRepository.findAllWithIcon()
                .stream()
                .map(this::toGame)
                .toList();
    }


    public Game toGame(GameJpaEntity gameJpa) {

        ResourceURL hostUrl = new ResourceURL(gameJpa.getCurrentHost());

        ImageResource icon = toImageResource(gameJpa.getIcon());
        ImageResource backgroundImage = toImageResource(gameJpa.getBackground());
        List<ImageResource> screenshots = gameJpa.getScreenshots()
                .stream()
                .map(screenshotJpa -> toImageResource(screenshotJpa.getScreenshot()))
                .toList();

        Set<Achievement> achievements = gameJpa.getAchievements()
                .stream()
                .map(this::toAchievement)
                .collect(Collectors.toSet());

        Set<Rule> rules = gameJpa.getRules()
                .stream()
                .map(this::toRule)
                .collect(Collectors.toSet());

        return new Game(
                new GameId(gameJpa.getGameId()),
                gameJpa.getTitle(),
                gameJpa.getDescription(),
                icon,
                backgroundImage,
                rules,
                hostUrl,
                screenshots,
                achievements
        );
    }

    public Achievement toAchievement(AchievementJpaEntity achievementJpa){
        return new Achievement(
                new AchievementId(achievementJpa.getId()),
                achievementJpa.getTitle(),
                achievementJpa.getCounterTotal(),
                achievementJpa.getDescription()
        );
    }

    public Rule toRule(RuleJpaEntity ruleJpa){
        return new Rule(
                ruleJpa.getStepNumber(),
                ruleJpa.getRule()
        );
    }

    public ImageResource toImageResource(ImageResourceJpaEntity imageResourceJpa) {
        return new ImageResource(new ResourceURL(imageResourceJpa.getUrl()));
    }
}



