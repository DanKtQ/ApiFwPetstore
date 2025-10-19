package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import restClient.RequestType;
import restClient.RestClient;

import java.security.Provider;

public class CommonApiService {

    // layer 2: defining the actions for user configs(layer 1)

    public Response post(Object body, String endpoint) {
        RequestSpecification requestSpecification = RestAssured.given();
        // for this kind of method we will do a POST with a body
        requestSpecification.body(body);
        ServiceHelper.requestLogs(requestSpecification, endpoint, RequestType.REQUEST_POST);
        Response response = performRequest(RequestType.REQUEST_POST, requestSpecification, endpoint);
        ServiceHelper.responseLogs(response);
        return response;
    }

    public Response get(String apiKey, String endpoint){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("api_key", apiKey);
        ServiceHelper.requestLogs(requestSpecification, endpoint, RequestType.REQUEST_GET);
        Response response = performRequest(RequestType.REQUEST_GET, requestSpecification, endpoint);
        ServiceHelper.responseLogs(response);
        return response;
    }

    public Response getOrder(String endpoint){
        RequestSpecification requestSpecification = RestAssured.given();
        ServiceHelper.requestLogs(requestSpecification, endpoint, RequestType.REQUEST_GET);
        Response response = performRequest(RequestType.REQUEST_GET, requestSpecification, endpoint);
        ServiceHelper.responseLogs(response);
        return response;
    }

    public Response delete(String apiKey, String endpoint){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("api_key", apiKey);
        ServiceHelper.requestLogs(requestSpecification, endpoint, RequestType.REQUEST_DELETE);
        Response response = performRequest(RequestType.REQUEST_DELETE, requestSpecification, endpoint);
        ServiceHelper.responseLogs(response);
        return response;
    }

    public Response deleteOrder(String endpoint){
        RequestSpecification requestSpecification = RestAssured.given();
        ServiceHelper.requestLogs(requestSpecification, endpoint, RequestType.REQUEST_DELETE);
        Response response = performRequest(RequestType.REQUEST_DELETE, requestSpecification, endpoint);
        ServiceHelper.responseLogs(response);
        return response;
    }

    // method to instantiate the link with layer1
    private Response performRequest(String requestType, RequestSpecification requestSpecification, String endpoint) {
        return new RestClient().performRequest(requestType, requestSpecification, endpoint);
    }

}
