package restClient;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

    // layer 1 = class where we define user configs
    // 2 actions to do:
    // 1 method to configure the client
    // 1 method to return a response based on user configs

    private RequestSpecification prepareClient(RequestSpecification requestSpecification) {
        requestSpecification.baseUri("https://petstore.swagger.io/");
        requestSpecification.contentType("application/json");
        requestSpecification.accept("application/json");
        return requestSpecification;
    }

    public Response performRequest(String requestType, RequestSpecification requestSpecification, String endpoint) {
        switch (requestType) {
            case RequestType.REQUEST_POST:
                return prepareClient(requestSpecification).post(endpoint);
            case RequestType.REQUEST_PUT:
                return prepareClient(requestSpecification).put(endpoint);
            case RequestType.REQUEST_GET:
                return prepareClient(requestSpecification).get(endpoint);
            case RequestType.REQUEST_DELETE:
                return prepareClient(requestSpecification).delete(endpoint);
        }
        return null;
    }

}
