package tests;

import actions.UserActions;
import extentUtility.ExtentUtility;
import extentUtility.ReportStep;
import hooks.Hooks;
import objectData.requestObject.RequestUser;
import objectData.responseObject.responseUser.ResponseUserSuccess;
import org.testng.Assert;
import org.testng.annotations.Test;
import propertyUtility.PropertyUtility;
import restClient.ResponseStatus;

public class CreateUserTest extends Hooks {

    public long userId;
    public String username;
    public String apiKey = "special-key";
    public RequestUser expectedUser;
    public UserActions userActions;

    @Test
    public void testUserMethod() {
        System.out.println("Step 1: CREATE New User");
        createUser();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "A new user account is created with success");

        System.out.println("\nStep 2: GET User by username");
        userActions.getUserByUsername(apiKey, username, ResponseStatus.SC_OK, expectedUser);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates the creation of a new user account with success");

        System.out.println("\nStep 3: DELETE user by username");
        deleteUserByUsername();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user deletes the new created user account with success");

        System.out.println("\nStep 4: RECHECK deleted user");
        userActions.getUserByUsername(apiKey, username, ResponseStatus.SC_NOT_FOUND, null);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates that the newly user account was deleted with success");
    }

    public void createUser() {
        userActions = new UserActions();
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createUserData");
        this.expectedUser = new RequestUser(propertyUtility.getAllData());
        this.username = expectedUser.getUsername();
        this.userId   = expectedUser.getId();
        ResponseUserSuccess responseUserBody = userActions.createNewUser(expectedUser);
        Assert.assertEquals(responseUserBody.getCode(), 200);
        Assert.assertEquals(responseUserBody.getType(), "unknown");
        Assert.assertEquals(responseUserBody.getMessage(), String.valueOf(userId));
    }

    public void deleteUserByUsername(){
        userActions.deleteUserByUsername(apiKey, username);
    }

//    public void createUser() {
//        // defining the user
//        RequestSpecification requestSpecification = RestAssured.given();
//        requestSpecification.baseUri("https://petstore.swagger.io/");
//        requestSpecification.contentType("application/json");
//        requestSpecification.accept("application/json");
//
//        // defining the request
//        PropertyUtility propertyUtility = new PropertyUtility("requestData/createUserData");
//        RequestUser requestUserBody = new RequestUser(propertyUtility.getAllData());
//        requestSpecification.body(requestUserBody);
//
//        // interacting with the response
//        Response response = requestSpecification.post("v2/user");
//        System.out.println(response.getStatusCode());
//        System.out.println(response.getStatusLine());
//
//        // validating the responseBody
//        ResponseUserSuccess responseUserBody = response.body().as(ResponseUserSuccess.class);
//        System.out.println(responseUserBody.getCode());
//        System.out.println(responseUserBody.getType());
//        System.out.println(responseUserBody.getMessage());
//        messageId = responseUserBody.getMessage();
//        Assert.assertEquals(response.getStatusCode(), responseUserBody.getCode());
//    }

}
