package engine.model;

import java.util.List;

public record QuizResponse(
        Integer id,
        String title,
        String text,
        List<String> options
) {
}
