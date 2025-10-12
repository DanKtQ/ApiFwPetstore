package actions;

import io.restassured.response.Response;
import objectData.requestObject.RequestPet;
import objectData.responseObject.ResponsePetGetFailed;
import objectData.responseObject.ResponsePetGetSuccess;
import objectData.responseObject.ResponsePetSuccess;
import org.testng.Assert;
import restClient.ResponseStatus;
import service.serviceImplementation.PetServiceImpl;

public class PetActions {

    private PetServiceImpl petServiceImpl;

    public PetActions() {
        petServiceImpl = new PetServiceImpl();
    }

    public ResponsePetSuccess createNewPet(RequestPet requestPet) {
        Response response = petServiceImpl.createPet(requestPet);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK);
        ResponsePetSuccess responsePetBody = response.body().as(ResponsePetSuccess.class);
        Assert.assertEquals(responsePetBody.getName(), requestPet.getName());
        return responsePetBody;
    }

    public void getPetById(String apiKey, long petId, RequestPet requestPetBody) {
        Response response = petServiceImpl.getSpecificPet(apiKey, petId);
        if (response.getStatusCode() == ResponseStatus.SC_OK) {
            Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK);
            ResponsePetGetSuccess responsePetGetSuccess = response.body().as(ResponsePetGetSuccess.class);
            Assert.assertEquals(responsePetGetSuccess.getId(), petId, "Body id must match requested petId");
        } else {
            // Expect 404
            Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_NOT_FOUND);
            ResponsePetGetFailed responsePetGetFailed = response.as(ResponsePetGetFailed.class);
            Assert.assertEquals(responsePetGetFailed.getMessage(), "Pet not found", "Unexpected error message");
            Assert.assertEquals(responsePetGetFailed.getType(), "error");
            Assert.assertEquals(responsePetGetFailed.getCode(), 1);
        }
    }

    public void deletePetById(String apiKey, long petId) {
        Response response = petServiceImpl.deleteSpecificPet(apiKey, petId);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK);
    }
}
