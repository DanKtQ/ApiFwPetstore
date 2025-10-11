package service.serviceImplementation;

import io.restassured.response.Response;
import objectData.requestObject.RequestPet;
import service.apiService.PetApiService;
import service.interfaceService.PetServiceInterface;

public class PetServiceImpl implements PetServiceInterface {

    // creating an instance of ApiService in order to access general methods(post, get.. from CommonApiService)
    private PetApiService petApiService;

    @Override
    public Response createPet(RequestPet body) {
        petApiService = new PetApiService();
        return petApiService.post(body, "v2/pet");
    }

    @Override
    public Response generateApiKey(RequestPet body) {
        petApiService = new PetApiService();
        return petApiService.post(body, "");
    }

    @Override
    public Response getSpecificPet(String apiKey, String petId) {
        return null;
    }

    @Override
    public Response deleteSpecificPet(String apiKey, String petId) {
        return null;
    }
}
