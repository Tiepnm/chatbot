package vertx.request;

import java.io.Serializable;

/**
 * Created by Administrator on 5/25/2017.
 */
public class BaseRequest implements Serializable {
    private String nameOfRequest;

    public BaseRequest() {
    }

    public BaseRequest(String nameOfRequest) {
        this.nameOfRequest = nameOfRequest;
    }

    public String getNameOfRequest() {
        return nameOfRequest;
    }

    public void setNameOfRequest(String nameOfRequest) {
        this.nameOfRequest = nameOfRequest;
    }
}
