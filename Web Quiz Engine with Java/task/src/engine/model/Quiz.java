package engine.model;

import java.util.List;

public record Quiz(
        Integer id,
        String title,
        String text,
        List<String> options,
        List<Integer> answer
) {
}
