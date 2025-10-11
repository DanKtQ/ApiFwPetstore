package objectData.responseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUserSuccess {

    @JsonProperty("code")
    private long code;

    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private String message;

    public long getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
