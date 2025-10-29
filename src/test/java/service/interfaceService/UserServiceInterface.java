package service.interfaceService;

import io.restassured.response.Response;
import objectData.requestObject.RequestUpdateUser;
import objectData.requestObject.RequestUser;

public interface UserServiceInterface {

    // this interface represents the actions which we want to do with a module(User)
    Response createUser(RequestUser body);

    Response getUserByUsername(String apiKey, String username);

    Response deleteUserByUsername(String apiKey, String username);

    Response updateUserByUsername(RequestUpdateUser body, String username);

}
