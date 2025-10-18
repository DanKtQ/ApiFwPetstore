package tests;

import actions.OrderActions;
import extentUtility.ExtentUtility;
import extentUtility.ReportStep;
import hooks.Hooks;
import objectData.requestObject.RequestOrder;
import objectData.responseObject.responseStore.ResponseOrderSuccess;
import org.testng.annotations.Test;
import propertyUtility.PropertyUtility;
import restClient.ResponseStatus;

public class CreateOrderTest extends Hooks {

    public long id;
    public long petId;
    public String apiKey = "special-key";
    public RequestOrder expectedOrder;
    public OrderActions orderActions;

    @Test
    public void testOrderMethod() {
        System.out.println("Step 1: CREATE New Order");
        createOrder();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "A new order is created with success");

        System.out.println("\nStep 2: GET Order by pet ID");
        orderActions.getOrderByPetId(apiKey, petId, ResponseStatus.SC_OK, expectedOrder);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates the creation of a new order with success");

        System.out.println("\nStep 3: DELETE order by order id");
        deleteOrderById();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user deletes purchase order with success");

        System.out.println("\nStep 4: RECHECK deleted order");
        orderActions.getOrderByPetId(apiKey, id, ResponseStatus.SC_NOT_FOUND, null);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates that the purchased order was deleted with success");
    }

    public void createOrder() {
        orderActions = new OrderActions();
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createOrderData");
        this.expectedOrder = new RequestOrder(propertyUtility.getAllData());
        ResponseOrderSuccess responseOrderBody = orderActions.createNewOrder(expectedOrder);
        this.petId = expectedOrder.getPetId();
        this.id = responseOrderBody.getId();
    }

    public void deleteOrderById(){
        orderActions.deleteOrderById(apiKey, id);
    }
}
