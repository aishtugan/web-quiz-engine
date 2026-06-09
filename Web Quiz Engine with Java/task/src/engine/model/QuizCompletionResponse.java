package engine.model;

import java.time.LocalDateTime;

public record QuizCompletionResponse(
        Integer id,
        LocalDateTime completedAt
) {
}
