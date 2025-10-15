package actions;

import io.restassured.response.Response;
import objectData.requestObject.RequestUser;
import objectData.responseObject.ResponseUserGetFailed;
import objectData.responseObject.ResponseUserGetSuccess;
import objectData.responseObject.ResponseUserSuccess;
import org.testng.Assert;
import restClient.ResponseStatus;
import service.serviceImplementation.UserServiceImpl;

public class UserActions {

    private UserServiceImpl userServiceImpl;

    public UserActions() {
        userServiceImpl = new UserServiceImpl();
    }

    public ResponseUserSuccess createNewUser(RequestUser requestUser) {
        Response response = userServiceImpl.createUser(requestUser);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK);
        ResponseUserSuccess responseUserBody = response.body().as(ResponseUserSuccess.class);
        Assert.assertEquals(responseUserBody.getCode(), 200);
        Assert.assertEquals(responseUserBody.getMessage(), String.valueOf(requestUser.getId()));
        return responseUserBody;
    }

    public void getUserByUsername(String apikey, String username, int expectedStatus, RequestUser expectedUser) {
        Response response = userServiceImpl.getUserByUsername(apikey, username);
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, expectedStatus, "Unexpected status for GET /user/" + username + ". Body:\n" + response.asString());

        switch (expectedStatus) {
            case ResponseStatus.SC_OK: { // 200
                ResponseUserGetSuccess body = response.as(ResponseUserGetSuccess.class);
                Assert.assertEquals(body.getUsername(), expectedUser.getUsername(), "Body username must match requested username");
                Assert.assertEquals(body.getFirstName(), expectedUser.getFirstName(), "Body firstname must match requested firstname");
                Assert.assertEquals(body.getLastName(), expectedUser.getLastName(), "Body lastname must match requested lastname");
                Assert.assertEquals(body.getEmail(), expectedUser.getEmail(), "Body email must match requested email");
                Assert.assertEquals(body.getPassword(), expectedUser.getPassword(), "Body password must match requested password");
                Assert.assertEquals(body.getPhone(), expectedUser.getPhone(), "Body phone must match requested phone");
                Assert.assertEquals(body.getUserStatus(), expectedUser.getUserStatus(), "Body user status must match requested user status");
                break;
            }
            case ResponseStatus.SC_NOT_FOUND: { // 404
                ResponseUserGetFailed err = response.as(ResponseUserGetFailed.class);
                Assert.assertEquals(err.getMessage(), "User not found", "Unexpected error message");
                Assert.assertEquals(err.getType(), "error");
                Assert.assertEquals(err.getCode(), 1);
                break;
            }
            case ResponseStatus.SC_INVALID: { // 400
                ResponseUserGetFailed err = response.as(ResponseUserGetFailed.class);
                Assert.assertTrue(
                        err.getMessage().toLowerCase().contains("invalid"),
                        "Expected an 'invalid id supplied' style message. Body: " + response.asString()
                );
                break;
            }
            default:
                Assert.fail("Handler missing for expected status: " + expectedStatus);
        }
    }

    public void deleteUserByUsername(String apiKey, String username) {
        Response response = userServiceImpl.deleteUserByUsername(apiKey, username);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK, "DELETE should be 200");
    }

}
