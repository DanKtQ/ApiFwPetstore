package tests;

import actions.PetActions;
import objectData.requestObject.RequestPet;
import objectData.responseObject.ResponsePetSuccess;
import org.testng.annotations.Test;
import propertyUtility.PropertyUtility;

public class CreatePetTest {

    public long petId;
    public RequestPet requestPetBody;
    public String apiKey = "special-key";
    public PetActions petActions;

    @Test
    public void testMethod(){
        System.out.println("Step 1: CREATE New Pet");
        createPet();
        System.out.println();

        System.out.println("Step 2: GET pet by Id");
        getPetById();
        System.out.println();

        System.out.println("Step 3: DELETE pet by Id");
        deletePetById();
        System.out.println();

        System.out.println("Step 4: RECHECK deleted pet");
        getPetById();
    }

    public void createPet() {
        petActions = new PetActions();
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createPetData");
        this.requestPetBody = new RequestPet(propertyUtility.getAllData());
        ResponsePetSuccess responsePetBody = petActions.createNewPet(requestPetBody);
        this.petId = responsePetBody.getId();
    }

    public void getPetById(){
        petActions.getPetById(apiKey, petId, requestPetBody);
    }

    public void deletePetById(){
        petActions.deletePetById(apiKey, petId);
    }
}
