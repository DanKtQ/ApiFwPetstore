package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import restClient.RequestType;
import restClient.RestClient;

public class CommonApiService {

    // layer 2: defining the actions for user configs(layer 1)

    public Response post(Object body, String endpoint) {
        RequestSpecification requestSpecification = RestAssured.given();
        // for this kind of method we will do a POST with a body
        requestSpecification.body(body);
        Response response = performRequest(RequestType.REQUEST_POST, requestSpecification, endpoint);
        return response;
    }

    public Response get(String apiKey, String endpoint){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("api_key", apiKey);
        Response response = performRequest(RequestType.REQUEST_GET, requestSpecification, endpoint);
        return response;
    }

    // method to instantiate the link with layer1
    private Response performRequest(String requestType, RequestSpecification requestSpecification, String endpoint) {
        return new RestClient().performRequest(requestType, requestSpecification, endpoint);
    }


}
