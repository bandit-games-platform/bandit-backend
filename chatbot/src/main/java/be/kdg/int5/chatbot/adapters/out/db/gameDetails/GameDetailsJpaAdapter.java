package be.kdg.int5.chatbot.adapters.out.db.gameDetails;

import be.kdg.int5.chatbot.adapters.out.db.gameRule.GameRuleJpaEntity;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.GameRule;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsSavePort;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
public class GameDetailsJpaAdapter implements GameDetailsLoadPort, GameDetailsSavePort {
    private final GameDetailsJpaRepository gameDetailsJpaRepository;

    public GameDetailsJpaAdapter(GameDetailsJpaRepository gameDetailsJpaRepository) {
        this.gameDetailsJpaRepository = gameDetailsJpaRepository;
    }

    @Override
    public GameDetails loadGameDetailsByGameId(GameId gameId) {
        final GameDetailsJpaEntity gameDetailsJpa = gameDetailsJpaRepository.findByIdWithRelationships(gameId.uuid());

        if (gameDetailsJpa == null) return null;

        Set<GameRule> gameRules = gameDetailsJpa.getGameRules().stream()
                .map(ruleJpa -> new GameRule(ruleJpa.getStepNumber(), ruleJpa.getRule()))
                .collect(Collectors.toSet());

        return new GameDetails(
                new GameId(gameDetailsJpa.getGameId()),
                gameDetailsJpa.getTitle(),
                gameDetailsJpa.getDescription(),
                gameRules
        );
    }

    @Override
    public void saveNewGameDetails(GameDetails gameDetails) {
        GameDetailsJpaEntity gameDetailsJpa = new GameDetailsJpaEntity(
                gameDetails.getId().uuid(),
                gameDetails.getTitle(),
                gameDetails.getDescription()
        );

        Set<GameRuleJpaEntity> gameRuleJpaEntities = gameDetails.getRules().stream().map(
                rule -> {
                    UUID ruleId = UUID.nameUUIDFromBytes((gameDetails.getId().uuid()+":"+rule.stepNumber()).getBytes(StandardCharsets.UTF_8));
                    return new GameRuleJpaEntity(ruleId, rule.stepNumber(), rule.rule(), gameDetailsJpa);
                }
        ).collect(Collectors.toSet());

        gameDetailsJpa.setGameRules(gameRuleJpaEntities);

        gameDetailsJpaRepository.save(gameDetailsJpa);
    }

    @Override
    public void updateGameDetails(GameDetails gameDetails) {
        GameDetailsJpaEntity gameDetailsJpaEntity = gameDetailsJpaRepository.findById(gameDetails.getId().uuid()).orElse(null);

        if (gameDetailsJpaEntity == null) {
            saveNewGameDetails(gameDetails);
            return;
        }

        Set<GameRuleJpaEntity> gameRuleJpaEntities = new HashSet<>();

        for (GameRule rule: gameDetails.getRules()) {
            boolean updated = false;
            for (GameRuleJpaEntity gameRuleJpaEntity: gameDetailsJpaEntity.getGameRules()) {
                if (rule.stepNumber() == gameRuleJpaEntity.getStepNumber()) {
                    gameRuleJpaEntity.setRule(rule.rule());
                    gameRuleJpaEntities.add(gameRuleJpaEntity);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                UUID ruleId = UUID.nameUUIDFromBytes((gameDetails.getId().uuid()+":"+rule.stepNumber()).getBytes(StandardCharsets.UTF_8));
                gameRuleJpaEntities.add(new GameRuleJpaEntity(
                        ruleId,
                        rule.stepNumber(),
                        rule.rule(),
                        gameDetailsJpaEntity
                ));
            }
        }

        gameDetailsJpaEntity.setGameRules(gameRuleJpaEntities);
        gameDetailsJpaEntity.setTitle(gameDetails.getTitle());
        gameDetailsJpaEntity.setDescription(gameDetails.getDescription());

        gameDetailsJpaRepository.save(gameDetailsJpaEntity);
    }
}
