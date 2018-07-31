package repository;

import entity.Answer;
import entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 6/5/2017.
 */
@Repository
public interface AnswerRepo extends PagingAndSortingRepository<Answer, Long> {
    @Query(value = "SELECT * from Answer a where a.id in (:answerIds)", nativeQuery = true)
    public List<Answer> findByIds(@Param("answerIds") List<Long> answerIds);


    @Query(value = "SELECT * from Answer a where a.answer = :answer", nativeQuery = true)
    public List<Answer> findByAnswer(@Param("answer") String answer);
}
