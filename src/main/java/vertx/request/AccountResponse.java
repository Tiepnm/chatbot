package vertx.request;

import java.io.Serializable;

/**
 * Created by Administrator on 5/24/2017.
 */
public class AccountResponse  implements Serializable  {
    private String result;

    public AccountResponse() {
        result = "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AccountResponse(String result) {
        this.result = result;
    }
}
