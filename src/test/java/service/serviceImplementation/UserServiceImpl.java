package service.serviceImplementation;

import io.restassured.response.Response;
import objectData.requestObject.RequestUpdateUser;
import objectData.requestObject.RequestUser;
import service.apiService.UserApiService;
import service.interfaceService.UserServiceInterface;

public class UserServiceImpl implements UserServiceInterface {

    // creating an instance of ApiService in order to access general methods(post, get.. from CommonApiService)
    private UserApiService userApiService;


    @Override
    public Response createUser(RequestUser body) {
        userApiService = new UserApiService();
        return userApiService.post(body, "v2/user");
    }

    @Override
    public Response getUserByUsername(String apiKey, String username) {
        userApiService = new UserApiService();
        String url = "v2/user/" + username;
        return userApiService.get(apiKey, url);
    }

    @Override
    public Response deleteUserByUsername(String apiKey, String username) {
        userApiService = new UserApiService();
        String url = "v2/user/" + username;
        return userApiService.delete(apiKey, url);
    }

    @Override
    public Response updateUserByUsername(RequestUpdateUser body, String username) {
        userApiService = new UserApiService();
        String url = "v2/user/" + username;
        return userApiService.put(body, url);
    }
}
