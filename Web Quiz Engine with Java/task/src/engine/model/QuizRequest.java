package engine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record QuizRequest(
        @NotBlank
        String title,

        @NotBlank
        String text,

        @NotNull
        @Size(min = 2)
        List<String> options,

        List<Integer> answer
) {
        public QuizRequest {
                if (answer == null) {
                        answer = List.of();
                }
        }
}
