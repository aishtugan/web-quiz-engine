package engine.service;

import engine.model.*;
import engine.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizResponse createQuiz(QuizRequest request) {
        Quiz quiz = new Quiz(
                0,
                request.title(),
                request.text(),
                request.options(),
                request.answer()
        );

        Quiz savedQuiz = quizRepository.saveInBase(quiz);

        return toResponse(savedQuiz);

    }

    public Optional<Quiz> findById(Integer id) {
        return quizRepository.findById(id);
    }

    private boolean hasValidAnswerIndexes(QuizRequest request) {
        return request.answer().stream()
                .allMatch(answer -> answer >= 0 && answer < request.options().size());
    }

    public QuizResult solveQuiz(Quiz quiz, AnswerRequest answerRequest) {

        boolean isCorrect = Set.copyOf(answerRequest.answer()).equals(Set.copyOf(quiz.answer()));
        return new QuizResult(isCorrect,
                                isCorrect ? "Congratulations, you're right!" : "Wrong answer! Please, try again.");
    }

    public List<QuizResponse> getAllAsResponse() {
        Collection<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public QuizResponse toResponse(Quiz quiz) {
        return new QuizResponse(
                quiz.id(),
                quiz.title(),
                quiz.text(),
                quiz.options()
        );
    }

}
