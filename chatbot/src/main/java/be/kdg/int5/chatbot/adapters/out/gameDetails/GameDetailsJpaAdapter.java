package be.kdg.int5.chatbot.adapters.out.gameDetails;

import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.GameRule;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class GameDetailsJpaAdapter implements GameDetailsLoadPort {
    private final GameDetailsJpaRepository gameDetailsJpaRepository;

    public GameDetailsJpaAdapter(GameDetailsJpaRepository gameDetailsJpaRepository) {
        this.gameDetailsJpaRepository = gameDetailsJpaRepository;
    }

    @Override
    public GameDetails loadGameDetailsByGameId(GameId gameId) {
        final GameDetailsJpaEntity gameDetailsJpa = gameDetailsJpaRepository.findById(gameId.uuid()).orElse(null);

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
}
