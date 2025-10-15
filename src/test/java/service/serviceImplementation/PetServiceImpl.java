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
    public Response getSpecificPet(String apiKey, long petId) {
        petApiService = new PetApiService();
        String url = "v2/pet/" + petId;
        return petApiService.get(apiKey, url);
    }

    @Override
    public Response deleteSpecificPet(String apiKey, long petId) {
        petApiService = new PetApiService();
        String url = "v2/pet/" + petId;
        return petApiService.delete(apiKey, url);
    }
}
