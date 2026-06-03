package engine.controller;

import engine.model.*;
import engine.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizResponse> postQuizzes(@Valid @RequestBody QuizRequest quizRequest) {

        QuizResponse quizResponse = quizService.createQuiz(quizRequest);
        return ResponseEntity.ok(quizResponse);

    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizResult> postSolveQuiz(@PathVariable Integer id, @RequestBody AnswerRequest answerRequest) {

        Quiz foundQuiz = quizService.findById(id).orElse(null);

        if (foundQuiz == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(quizService.solveQuiz(foundQuiz, answerRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Integer id) {
        Quiz foundQuiz = quizService.findById(id).orElse(null);

        if (foundQuiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizService.toResponse(foundQuiz));
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getQuizzes() {
        return ResponseEntity.ok(quizService.getAllAsResponse());
    }
}
