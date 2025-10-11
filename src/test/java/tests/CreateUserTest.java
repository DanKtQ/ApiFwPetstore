package tests;

import propertyUtility.PropertyUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objectData.requestObject.RequestUser;
import objectData.responseObject.ResponseUserSuccess;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest {

    public String messageId;

    @Test
    public void testMethod() {
        System.out.println("Step 1: Create New User");
        createUser();
        System.out.println();

        System.out.println(("Step 2: Generate new token"));

    }

    public void createUser() {
        // defining the user
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://petstore.swagger.io/");
        requestSpecification.contentType("application/json");
        requestSpecification.accept("application/json");

        // defining the request
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createUserData");
        RequestUser requestUserBody = new RequestUser(propertyUtility.getAllData());
        requestSpecification.body(requestUserBody);

        // interacting with the response
        Response response = requestSpecification.post("v2/user");
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());

        // validating the responseBody
        ResponseUserSuccess responseUserBody = response.body().as(ResponseUserSuccess.class);
        System.out.println(responseUserBody.getCode());
        System.out.println(responseUserBody.getType());
        System.out.println(responseUserBody.getMessage());
        messageId = responseUserBody.getMessage();
        Assert.assertEquals(response.getStatusCode(), responseUserBody.getCode());
    }
}
