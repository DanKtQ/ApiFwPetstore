package tests;

import propertyUtility.PropertyUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objectData.requestObject.RequestPet;
import objectData.responseObject.ResponsePetGetSuccess;
import objectData.responseObject.ResponsePetSuccess;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreatePetTest {

    public long petId;
    public RequestPet requestPetBody;
    public String apiKey = "special-key";

    @Test
    public void testMethod(){
        System.out.println("Step 1: Create New Pet");
        createPet();
        System.out.println();


        System.out.println("Step 2: GET pet by Id");
        getPetById();
        System.out.println();
    }

    public void createPet() {
        // defining the user
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://petstore.swagger.io/");
        requestSpecification.contentType("application/json");
        requestSpecification.accept("application/json");
        requestSpecification.auth().oauth2("dummy-token");

        // defining the request
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createPetData");
        RequestPet requestPetBody = new RequestPet(propertyUtility.getAllData());
        requestSpecification.body(requestPetBody);

        // interacting with the response
        Response response = requestSpecification.post("v2/pet");
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());

        // validating the responseBody
        ResponsePetSuccess responsePetBody = response.body().as(ResponsePetSuccess.class);
        System.out.println("Pet id: " + responsePetBody.getId());
        System.out.println("Pet category id: " + responsePetBody.getCategory().getId());
        System.out.println("Pet category name: " + responsePetBody.getCategory().getName());
        System.out.println("Pet name: " + responsePetBody.getName());
        System.out.println("Pet photo urls: " + responsePetBody.getPhotoUrls());
        System.out.println("Pet tags: " + responsePetBody.getTags().get(0).getId() + " "
                + responsePetBody.getTags().get(0).getName() + ", "
                + responsePetBody.getTags().get(1).getId() + " "
                + responsePetBody.getTags().get(1).getName());
        System.out.println("Pet status: " + responsePetBody.getStatus());
        petId = responsePetBody.getId();
        Assert.assertEquals(responsePetBody.getName(), requestPetBody.getName());
    }

    public void getPetById(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://petstore.swagger.io/");
        requestSpecification.contentType("application/json");
        requestSpecification.header("api_key", apiKey);

        Response response = requestSpecification.get("v2/pet/" + petId);

        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(response.getStatusLine());

        ResponsePetGetSuccess responsePetGetSuccess = response.body().as(ResponsePetGetSuccess.class);
        System.out.println(responsePetGetSuccess.getId());

        response.then().log().all();
    }
}
