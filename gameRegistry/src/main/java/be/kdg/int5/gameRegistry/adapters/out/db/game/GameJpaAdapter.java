package be.kdg.int5.gameRegistry.adapters.out.db.game;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.adapters.out.db.achievement.AchievementJpaEntity;
import be.kdg.int5.gameRegistry.adapters.out.db.developer.DeveloperJpaEntity;
import be.kdg.int5.gameRegistry.domain.*;
import be.kdg.int5.gameRegistry.port.out.GamesCreatePort;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import be.kdg.int5.gameRegistry.port.out.GamesUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GameJpaAdapter implements GamesLoadPort, GamesCreatePort, GamesUpdatePort {

    private final GameJpaRepository gameJpaRepository;

    public GameJpaAdapter(GameJpaRepository gameJpaRepository) {
        this.gameJpaRepository = gameJpaRepository;
    }

    @Override
    public Game loadGameByIdWithDetails(UUID gameId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByIdWithAllRelationships(gameId);

        if (gameJpaEntity == null) return null;
        return toGame(gameJpaEntity);
    }

    @Override
    public List<Game> loadAllGamesWithIcon() {
        return gameJpaRepository.findAll()
                .stream()
                .map(this::toGame)
                .toList();
    }

    @Override
    public boolean create(Game newGame) {
        if (gameJpaRepository.existsById(newGame.getId().uuid())) return false;

        gameJpaRepository.save(toGameEntity(newGame));
        return true;
    }

    @Override
    public boolean update(Game game) {
        if (!gameJpaRepository.existsById(game.getId().uuid())) return false;

        gameJpaRepository.save(toGameEntity(game));
        return true;
    }

    public GameJpaEntity toGameEntity(Game game) {
        UUID gameId = game.getId().uuid();

        return new GameJpaEntity(
                gameId,
                game.getTitle(),
                game.getDescription(),
                game.getCurrentPrice(),
                toImageResourceEntity(game.getIcon()),
                toImageResourceEntity(game.getBackground()),
                game.getRules().stream().map(this::toRuleEntity).collect(Collectors.toSet()),
                game.getCurrentHost().url(),
                toDeveloperEntity(game.getDeveloper()),
                game.getScreenshots().stream().map(this::toImageResourceEntity).collect(Collectors.toSet()),
                game.getAchievements().stream().map(achievement -> toAchievementEntity(achievement, gameId)).collect(Collectors.toSet())
        );
    }

    public Game toGame(GameJpaEntity gameJpa) {

        ResourceURL hostUrl = new ResourceURL(gameJpa.getCurrentHost());

        ImageResource icon = toImageResource(gameJpa.getIcon());
        ImageResource backgroundImage = toImageResource(gameJpa.getBackground());
        List<ImageResource> screenshots = gameJpa.getScreenshots()
                .stream()
                .map(this::toImageResource)
                .toList();

        Set<Achievement> achievements = gameJpa.getAchievements()
                .stream()
                .map(this::toAchievement)
                .collect(Collectors.toSet());

        Set<Rule> rules = gameJpa.getRules()
                .stream()
                .map(this::toRule)
                .collect(Collectors.toSet());
        Developer developer = new Developer(new DeveloperId(gameJpa.getDeveloper().getId()), gameJpa.getDeveloper().getStudioName());

        return new Game(
                new GameId(gameJpa.getId()),
                gameJpa.getTitle(),
                gameJpa.getDescription(),
                gameJpa.getCurrentPrice(),
                icon,
                backgroundImage,
                rules,
                hostUrl,
                developer,
                screenshots,
                achievements
        );
    }

    public DeveloperJpaEntity toDeveloperEntity(Developer developer) {
        return new DeveloperJpaEntity(
                developer.id().uuid(),
                developer.studioName()
        );
    }

    public AchievementJpaEntity toAchievementEntity(Achievement achievement, UUID gameId) {
        return new AchievementJpaEntity(
                achievement.getId().uuid(),
                achievement.getTitle(),
                achievement.getCounterTotal(),
                achievement.getDescription(),
                gameId
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

    public RuleJpaEmbeddable toRuleEntity(Rule rule){
        return new RuleJpaEmbeddable(
                rule.stepNumber(),
                rule.rule()
        );
    }

    public Rule toRule(RuleJpaEmbeddable ruleJpa){
        return new Rule(
                ruleJpa.getStepNumber(),
                ruleJpa.getRule()
        );
    }

    public ImageResourceJpaEmbeddable toImageResourceEntity(ImageResource imageResource) {
        return new ImageResourceJpaEmbeddable(
                imageResource.url().url()
        );
    }

    public ImageResource toImageResource(ImageResourceJpaEmbeddable imageResourceJpa) {
        return new ImageResource(new ResourceURL(imageResourceJpa.getUrl()));
    }
}



