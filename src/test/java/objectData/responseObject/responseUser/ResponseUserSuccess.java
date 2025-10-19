package objectData.responseObject.responseUser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUserSuccess {

    private int code;

    private String type;

    private String message;

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
