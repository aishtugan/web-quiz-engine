package engine.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String text;

    @ElementCollection
    @CollectionTable(
            name = "quiz_options",
            joinColumns = @JoinColumn(name = "quiz_id")
    )
    @Column(name = "option_text")
    @OrderColumn(name = "option_order")
    @Fetch(FetchMode.SUBSELECT)
    private List<String> options = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "quiz_answers",
            joinColumns = @JoinColumn(name = "quiz_id")
    )
    @Column(name = "answer_index")
    @OrderColumn(name = "answer_order")
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> answer = new ArrayList<>();

    public Quiz() {

    }

    public Quiz(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

}

