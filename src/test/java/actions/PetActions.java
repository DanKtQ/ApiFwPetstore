package actions;

import io.restassured.response.Response;
import objectData.requestObject.RequestPet;
import objectData.responseObject.responsePet.ResponsePetGetFailed;
import objectData.responseObject.responsePet.ResponsePetGetSuccess;
import objectData.responseObject.responsePet.ResponsePetSuccess;
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

    public void getPetById(String apiKey, long petId, int expectedStatus, RequestPet expectedPet) {
        Response response = petServiceImpl.getSpecificPet(apiKey, petId);
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, expectedStatus, "Unexpected status for GET /pet/" + petId + ". Body:\n" + response.asString());

        switch (expectedStatus) {
            case ResponseStatus.SC_OK: { // 200
                ResponsePetGetSuccess body = response.as(ResponsePetGetSuccess.class);
                Assert.assertEquals(body.getId(), petId, "Body id must match requested petId");
//                if (expectedPet != null) {
//                    Assert.assertEquals(body.getName(), expectedPet.getName(), "Name mismatch");
//                    Assert.assertEquals(body.getCategory().getName(), expectedPet.getCategory().getName(), "Category name mismatch");
//                }
                break;
            }
            case ResponseStatus.SC_NOT_FOUND: { // 404
                ResponsePetGetFailed err = response.as(ResponsePetGetFailed.class);
                Assert.assertEquals(err.getMessage(), "Pet not found", "Unexpected error message");
                Assert.assertEquals(err.getType(), "error");
                Assert.assertEquals(err.getCode(), 1);
                break;
            }
            case ResponseStatus.SC_INVALID: { // 400
                ResponsePetGetFailed err = response.as(ResponsePetGetFailed.class);
                Assert.assertTrue(
                        err.getMessage().toLowerCase().contains("invalid"),
                        "Expected an 'invalid id supplied' style message. Body: " + response.asString()
                );
                break;
            }
            default:
                Assert.fail("Handler missing for expected status: " + expectedStatus);
        }
    }

    public void deletePetById(String apiKey, long petId) {
        Response response = petServiceImpl.deleteSpecificPet(apiKey, petId);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK, "DELETE should be 200");
    }
}
