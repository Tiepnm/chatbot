package vertx.request;

/**
 * Created by Administrator on 6/5/2017.
 */
public class QuestionRequest  extends BaseRequest  {
    private String question;
    private String userAnswer;
    private String answerPrivous;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getAnswerPrivous() {
        return answerPrivous;
    }

    public void setAnswerPrivous(String answerPrivous) {
        this.answerPrivous = answerPrivous;
    }
}
