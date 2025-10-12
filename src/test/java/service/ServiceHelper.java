package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import loggerUtility.LoggerUtility;

public class ServiceHelper {

    // method to log info about request
    // method to log info about response

    public static void requestLogs(RequestSpecification requestSpecification, String path, String methodType) {
        LoggerUtility.infoTest("=====Request info=====");
        LoggerUtility.infoTest(getRequestURL(path));
        LoggerUtility.infoTest(getRequestMethod(methodType));
        LoggerUtility.infoTest(getRequestBody(requestSpecification));
    }

    public static void responseLogs(Response response) {
        LoggerUtility.infoTest("=====Response info=====");
        LoggerUtility.infoTest(getResponseStatusLine(response));
        LoggerUtility.infoTest(getResponseStatusCode(response));
        LoggerUtility.infoTest(getResponseTime(response));
        LoggerUtility.infoTest(getResponseBody(response));
    }

    private static String getRequestURL(String path) {
        return "Request URI: https://petstore.swagger.io/" + path;
    }

    private static String getRequestMethod(String methodType) {
        return "Request METHOD: " + methodType;
    }

    private static String getRequestBody(RequestSpecification requestSpecification) {
        String json = "";
        Object requestBody = ((RequestSpecificationImpl) requestSpecification).getBody();
        if (requestBody != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                json = objectMapper.readTree(requestBody.toString()).toPrettyString();
            } catch (JsonProcessingException ignored) {
            }
        }
        return "Request BODY: \n" + json;
    }

    private static String getResponseStatusLine(Response response){
        return "Response STATUS LINE: " + response.getStatusLine();
    }

    private static String getResponseStatusCode(Response response){
        return "Response STATUS CODE: " + response.getStatusCode();
    }

    private static String getResponseTime(Response response){
        return "Response TIME: " + response.getTime();
    }

    private static String getResponseBody(Response response){
        if (response.getBody() != null){
            return "Response BODY: \n" + response.getBody().asPrettyString();
        }
        else {
            return "";
        }
    }
}
