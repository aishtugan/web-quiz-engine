package engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


@SpringBootApplication
@RestController
public class WebQuizEngine {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }

    final Integer rightAnswer = 2;
    public record QuizQuestion(
            Integer id,
            String title,
            String text,
            String[] options
    ) { }

    public record QuizQuestionWithAnswer(
            Integer id,
            String title,
            String text,
            String[] options,
            Integer answer
    ) { }

    public static class QuizRequest {

        private final String title;
        private final String text;
        private final String[] options;
        private final Integer answer;

        public QuizRequest(String title, String text, String[] options, Integer answer) {
            this.title = title;
            this.text = text;
            this.options = options;
            this.answer = answer;
        }
        public String getTitle() {
            return title;
        }
        public String getText() {
            return text;
        }
        public String[] getOptions() {
            return options;
        }
        public Integer getAnswer() {
            return answer;
        }

    }

    public record QuizQuestionWithoutAnswer(
            String title,
            String text,
            String[] options
    ) { }

    public record QuizResult(
            boolean success,
            String feedback
    ) { }

    public HashMap<Integer, QuizQuestionWithAnswer> questionsBase = new HashMap<Integer, QuizQuestionWithAnswer>();

    @GetMapping("/api/quiz")
    public ResponseEntity<QuizQuestionWithoutAnswer> getQuiz() {
        QuizQuestionWithoutAnswer question = new QuizQuestionWithoutAnswer(
                "The Java Logo",
                "What is depicted on the Java logo?",
                new String[] { "Robot","Tea leaf","Cup of coffee","Bug" }
        );
        return ResponseEntity.ok(question);
    }

    @PostMapping("/api/quiz")
    public ResponseEntity<QuizResult> postQuiz(@RequestParam() Integer answer) {
        QuizResult result = new QuizResult(Objects.equals(answer, rightAnswer), Objects.equals(answer, rightAnswer) ? "Congratulations, you're right!" : "Wrong answer! Please, try again.");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizQuestion> postQuizzes(@RequestBody QuizRequest quizRequest) {

        Integer answer = quizRequest.getAnswer();
        String[] options = quizRequest.getOptions();

        Integer id = questionsBase.size() + 1;
        QuizQuestionWithAnswer question = new QuizQuestionWithAnswer(id, quizRequest.getTitle(), quizRequest.getText(), options, answer);
        QuizQuestion originalQuestion = new QuizQuestion(id, quizRequest.getTitle(), quizRequest.getText(), options);
        questionsBase.put(id, question);

        return ResponseEntity.ok(originalQuestion);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<QuizResult> postSolveQuiz(@PathVariable Integer id, @RequestParam Integer answer) {
        QuizQuestionWithAnswer question = questionsBase.get(id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        QuizResult result = new QuizResult(Objects.equals(answer, question.answer),
                Objects.equals(answer, question.answer) ? "Congratulations, you're right!" : "Wrong answer! Please, try again.");
        return ResponseEntity.ok(result);

    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizQuestion> getQuizById(@PathVariable Integer id) {
        QuizQuestionWithAnswer question = questionsBase.get(id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new QuizQuestion(question.id, question.title, question.text, question.options));
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<ArrayList<QuizQuestion>> getQuizzes() {

        ArrayList<QuizQuestion> questions = new ArrayList<>();
        for (QuizQuestionWithAnswer question : questionsBase.values()) {
            questions.add(new QuizQuestion(question.id, question.title, question.text, question.options));
        }
        return ResponseEntity.ok(questions);
    }

}
