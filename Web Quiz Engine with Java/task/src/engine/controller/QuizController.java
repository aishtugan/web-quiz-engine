package engine.controller;

import engine.model.*;
import engine.service.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizResponse> postQuizzes(@Valid @RequestBody QuizRequest quizRequest, Authentication authentication) {

        QuizResponse quizResponse = quizService.createQuiz(quizRequest, authentication.getName());
        return ResponseEntity.ok(quizResponse);

    }

    @PostMapping("/api/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {

        quizService.createUser(userRequest);
        return ResponseEntity.ok("User created successfully!");

    }

    @PostMapping("/actuator/shutdown")
    public ResponseEntity<String> actuatorShutdown() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<QuizResult> postSolveQuiz(@PathVariable Integer id, @RequestBody AnswerRequest answerRequest, Authentication authentication) {

        Quiz foundQuiz = quizService.findById(id).orElse(null);

        if (foundQuiz == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quizService.solveQuiz(foundQuiz, answerRequest, authentication.getName()));

    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Integer id) {
        Quiz foundQuiz = quizService.findById(id).orElse(null);

        if (foundQuiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizService.toResponse(foundQuiz));
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<QuizResponse>> getQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(quizService.getQuizzes(page));
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<QuizCompletionResponse>> getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page, Authentication authentication) {
        return ResponseEntity.ok(quizService.getCompletedResponses(authentication.getName(), page));
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id, Authentication authentication) {

        if (quizService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        quizService.deleteQuiz(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
