package engine.repository;

import engine.model.Quiz;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class QuizRepository {

    private final Map<Integer, Quiz> quizBase = new HashMap<>();
    private int nextId = 1;

    public Quiz saveInBase(Quiz quiz) {
        Quiz quizWithId = new Quiz(nextId++, quiz.title(), quiz.text(), quiz.options(), quiz.answer());
        quizBase.put(quizWithId.id(), quizWithId);
        return quizWithId;
    }

    public Optional<Quiz> findById(int id) {
        return Optional.ofNullable(quizBase.get(id));
    }

    public Collection<Quiz> findAll() {
        return quizBase.values();
    }

}
