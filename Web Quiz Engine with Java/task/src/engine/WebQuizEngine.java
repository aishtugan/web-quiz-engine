package engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class WebQuizEngine {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }

    public record QuizQuestion(
            String title,
            String text,
            String[] options
    ) { }

    public record QuizResult(
            boolean success,
            String feedback
    ) { }

    @GetMapping("/api/quiz")
    public ResponseEntity<QuizQuestion> getQuiz() {
        QuizQuestion question = new QuizQuestion(
                "The Java Logo",
                "What is depicted on the Java logo?",
                new String[] { "Robot","Tea leaf","Cup of coffee","Bug" }
        );
        return ResponseEntity.ok(question);
    }

    @PostMapping("/api/quiz")
    public ResponseEntity<QuizResult> postQuiz(@RequestParam(required = true) Integer answer) {
        QuizResult result = new QuizResult(answer == 2, answer == 2 ? "Congratulations, you're right!" : "Wrong answer! Please, try again.");
        return ResponseEntity.ok(result);
    }

}
