package repository;

import entity.Question;
import entity.QuestionHasAnswer;
import entity.QuestionHasAnswerComposite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tiep on 6/5/2017.
 */
@Repository
public interface QuestionHasAnswerRepo extends PagingAndSortingRepository<entity.QuestionHasAnswer, Long>  {
    @Query(value = "SELECT * from Question_Has_Answer q where q.questionId = :questionId", nativeQuery = true)
    List<QuestionHasAnswer> findByQuestionId(@Param("questionId") Long questionId);
}
