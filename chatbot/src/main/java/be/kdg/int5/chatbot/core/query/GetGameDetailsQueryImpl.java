package be.kdg.int5.chatbot.core.query;

import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.ports.in.query.GameDetailsQuery;
import be.kdg.int5.chatbot.ports.in.query.GetGameDetailsCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGameDetailsQueryImpl implements GameDetailsQuery {

    @Override
    @Transactional(readOnly = true)
    public GameDetails loadGameDetails(GetGameDetailsCommand getGameDetailsCommand) {
        return null;
    }
}
