package entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 6/2/2017.
 */
@Entity
@Table(name="Question_Has_Answer")
public class QuestionHasAnswer {
    @Id
    @EmbeddedId
    private QuestionHasAnswerComposite key;

    public QuestionHasAnswerComposite getKey() {
        return key;
    }

    public void setKey(QuestionHasAnswerComposite key) {
        this.key = key;
    }
}
