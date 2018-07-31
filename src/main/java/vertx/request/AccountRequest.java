package vertx.request;

import java.io.Serializable;

/**
 * Created by Administrator on 5/24/2017.
 */

public class AccountRequest extends BaseRequest{
    private String id;

    public String getId() {
        return id;
    }

    public AccountRequest(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
