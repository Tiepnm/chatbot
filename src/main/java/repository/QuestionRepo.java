package repository;

import entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 6/2/2017.
 */
@Repository
public interface QuestionRepo extends PagingAndSortingRepository<Question, Long> {

    @Query(value = "SELECT * from Question q where LOWER(q.question) = LOWER(:question)", nativeQuery = true)
    public List<Question> findByQuestion(@Param("question") String question);
}
