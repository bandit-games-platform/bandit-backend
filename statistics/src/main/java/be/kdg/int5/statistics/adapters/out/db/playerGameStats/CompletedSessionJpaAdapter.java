package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.domain.SessionId;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompletedSessionJpaAdapter implements CompletedSessionLoadPort {
    private final CompletedSessionJpaRepository completedSessionJpaRepository;

    public CompletedSessionJpaAdapter(CompletedSessionJpaRepository completedSessionJpaRepository) {
        this.completedSessionJpaRepository = completedSessionJpaRepository;
    }

    @Override
    public List<CompletedSession> loadAllCompletedSessionsForPlayer(PlayerId playerId) {
        List<CompletedSessionJpaEntity> allSessions = completedSessionJpaRepository.findAllByPlayerId(playerId.uuid());
        return allSessions.stream().map(this::completedSessionJpaToDomain).collect(Collectors.toList());
    }

    @Override
    public List<CompletedSession> loadAllCompletedSessionsForGameAndPlayer(GameId gameId, PlayerId playerId) {
        List<CompletedSessionJpaEntity> allSessions = completedSessionJpaRepository.findAllByGameIdAndPlayerId(gameId.uuid(),playerId.uuid());
        return allSessions.stream().map(this::completedSessionJpaToDomain).collect(Collectors.toList());
    }

    private CompletedSession completedSessionJpaToDomain(final CompletedSessionJpaEntity completedSessionJpaEntity){
        return new CompletedSession(
                new SessionId(completedSessionJpaEntity.getSessionId()),
                completedSessionJpaEntity.getStartTime(),
                completedSessionJpaEntity.getEndTime(),
                completedSessionJpaEntity.getEndState(),
                completedSessionJpaEntity.getTurnsTaken(),
                completedSessionJpaEntity.getAvgSecondsPerTurn(),
                completedSessionJpaEntity.getPlayerScore(),
                completedSessionJpaEntity.getOpponentScore(),
                completedSessionJpaEntity.getClicks(),
                completedSessionJpaEntity.getCharacter(),
                completedSessionJpaEntity.isWasFirstToGo()
        );
    }
}
