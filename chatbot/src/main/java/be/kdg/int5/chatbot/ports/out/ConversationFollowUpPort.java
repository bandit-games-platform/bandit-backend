package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;

public interface ConversationFollowUpPort {
    Answer followUpOnGameConversation(GameDetails gameDetails, GameConversation gameConversation, Question question);
}
