package engine.service;

import engine.model.*;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public QuizResponse createQuiz(QuizRequest request, String userEmail) {
        Quiz quiz = new Quiz(
                request.title(),
                request.text(),
                request.options(),
                request.answer(),
                userEmail
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

    public void deleteQuiz(Integer id, String userEmail) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        if (!quiz.getUserEmail().equals(userEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this quiz");
        }

        quizRepository.deleteById(id);
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

    public void createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);
    }

    public boolean passwordMatches(String rawPassword, User user) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

}
