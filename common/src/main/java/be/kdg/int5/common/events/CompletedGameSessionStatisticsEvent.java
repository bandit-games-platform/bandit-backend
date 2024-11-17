package be.kdg.int5.common.events;

import be.kdg.int5.common.domain.GameEndState;
import be.kdg.int5.common.domain.GameId;
import be.kdg.int5.common.domain.PlayerId;
import be.kdg.int5.common.domain.SessionId;

public record CompletedGameSessionStatisticsEvent(
        PlayerId playerId,
        GameId gameId,
        SessionId sessionId,
        String startTime,
        String endTime,
        GameEndState endState,
        Integer turnsTaken,
        Double avgSecondsPerTurn,
        Integer opponentScore,
        Integer clicks,
        String character,
        Boolean wasFirstToGo
        ) {
}
