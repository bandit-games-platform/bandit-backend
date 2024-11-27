package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;

import java.util.List;

public interface AnswerAskPort {
    Answer getAnswerForInitialQuestion(GameDetails gameDetails, Question question);

    Answer getAnswerForFollowUpQuestion(GameDetails gameDetails, List<Question> previousQuestionWindowList, Question question);
}
