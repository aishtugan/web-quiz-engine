package engine.repository;

import engine.model.QuizHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizHistoryRepository extends JpaRepository<QuizHistory, Long> {
    Page<QuizHistory> findByUserEmailOrderByCompletedAtDesc(String userEmail, Pageable pageable);
}
