package tests;

import actions.PetActions;
import extentUtility.ExtentUtility;
import extentUtility.ReportStep;
import hooks.Hooks;
import objectData.requestObject.RequestPet;
import objectData.responseObject.ResponsePetSuccess;
import org.testng.annotations.Test;
import propertyUtility.PropertyUtility;
import restClient.ResponseStatus;

public class CreatePetTest extends Hooks {

    public long petId;
    public String apiKey = "special-key";
    public RequestPet expectedPet;
    public PetActions petActions;

    @Test
    public void testMethod(){
        System.out.println("Step 1: CREATE New Pet");
        createPet();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user creates a new pet account with success");

        System.out.println("\nStep 2: GET pet by Id");
        petActions.getPetById(apiKey, petId, ResponseStatus.SC_OK, expectedPet);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates the creation of a new pet account with success");

        System.out.println("\nStep 3: DELETE pet by Id");
        deletePetById();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user deletes the new created pet account with success");

        System.out.println("\nStep 4: RECHECK deleted pet");
        petActions.getPetById(apiKey, petId, ResponseStatus.SC_NOT_FOUND, null);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates that the newly pet account was deleted with success");
    }

    public void createPet() {
        petActions = new PetActions();
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createPetData");
        this.expectedPet = new RequestPet(propertyUtility.getAllData());
        ResponsePetSuccess responsePetBody = petActions.createNewPet(expectedPet);
        this.petId = responsePetBody.getId();
    }

    public void deletePetById(){
        petActions.deletePetById(apiKey, petId);
    }
}
