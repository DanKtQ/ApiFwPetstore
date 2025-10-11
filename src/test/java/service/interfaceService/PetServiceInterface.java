package service.interfaceService;

import io.restassured.response.Response;
import objectData.requestObject.RequestPet;

public interface PetServiceInterface {

    // this interface represents the actions which we want to do with a module(Pet)
    Response createPet(RequestPet body);

    Response generateApiKey(RequestPet body);

    Response getSpecificPet(String apiKey, String petId);

    Response deleteSpecificPet(String apiKey, String petId);

}
