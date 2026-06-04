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
                request.title(),
                request.text(),
                request.options(),
                request.answer()
        );

        Quiz savedQuiz = quizRepository.save(quiz);

        return toResponse(savedQuiz);

    }

    public Optional<Quiz> findById(Integer id) {
        return quizRepository.findById(id);
    }

    public QuizResult solveQuiz(Quiz quiz, AnswerRequest answerRequest) {

        boolean isCorrect = Set.copyOf(answerRequest.answer()).equals(Set.copyOf(quiz.getAnswer()));
        return new QuizResult(isCorrect,
                                isCorrect ? "Congratulations, you're right!" : "Wrong answer! Please, try again.");
    }

    public List<QuizResponse> getAllAsResponse() {

        return quizRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public QuizResponse toResponse(Quiz quiz) {
        return new QuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getText(),
                quiz.getOptions()
        );
    }

}
