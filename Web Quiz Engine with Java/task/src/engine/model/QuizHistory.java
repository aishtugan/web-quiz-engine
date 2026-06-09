package engine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class QuizHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quizId;
    private String userEmail;
    private LocalDateTime completedAt;

    public QuizHistory() {

    }

    public QuizHistory(Integer quizId, String userEmail, LocalDateTime completedAt) {
        this.quizId = quizId;
        this.userEmail = userEmail;
        this.completedAt = completedAt;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }


}
