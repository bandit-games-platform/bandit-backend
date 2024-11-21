package be.kdg.int5.chatbot.core.query;

import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.ports.in.query.InitialQuestionForConversation;
import org.springframework.stereotype.Service;

@Service
public class GetInitialQuestionForConversation implements InitialQuestionForConversation {

    @Override
    public String getInitialQuestionForGameConversation() {
        return GameConversation.initialPrompt;
    }
}
