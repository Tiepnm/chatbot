package vertx.request;

import java.io.Serializable;

/**
 * Created by Administrator on 6/5/2017.
 */
public class AnswerResponse implements Serializable{
    private String answer;
    private boolean provideExplainFromUser;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isProvideExplainFromUser() {
        return provideExplainFromUser;
    }

    public void setProvideExplainFromUser(boolean provideExplainFromUser) {
        this.provideExplainFromUser = provideExplainFromUser;
    }
}
