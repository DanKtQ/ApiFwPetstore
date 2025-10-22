package objectData.responseObject.responseUser;

import objectData.ResponseNotNull;
import org.testng.Assert;

public class ResponseUserSuccess implements ResponseNotNull {

    @Override
    public void validateNotNullFields() {
        Assert.assertNotNull(code);
        Assert.assertNotNull(type);
        Assert.assertNotNull(message);
    }
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
