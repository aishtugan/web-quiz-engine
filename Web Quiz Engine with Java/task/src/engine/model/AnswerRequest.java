package engine.model;

import java.util.List;

public record AnswerRequest(
        List<Integer> answer
) {
    public AnswerRequest {
        if (answer == null) {
            answer = List.of();
        }
    }
}
