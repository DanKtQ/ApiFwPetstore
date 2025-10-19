package tests;

import actions.OrderActions;
import extentUtility.ExtentUtility;
import extentUtility.ReportStep;
import hooks.Hooks;
import io.restassured.response.Response;
import objectData.requestObject.RequestOrder;
import objectData.responseObject.responseStore.ResponseOrderGetSuccess;
import objectData.responseObject.responseStore.ResponseOrderSuccess;
import org.testng.Assert;
import org.testng.annotations.Test;
import propertyUtility.PropertyUtility;
import restClient.ResponseStatus;

public class CreateOrderTest extends Hooks {

    public int id;
    public int petId;
    public String apiKey = "special-key";
    public RequestOrder expectedOrder;
    public OrderActions orderActions;

    @Test
    public void testOrderMethod() {
        System.out.println("Step 1: CREATE New Order");
        createOrder();
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "A new order is created with success");

        System.out.println("\nStep 2: GET Order by ID");    //GET by ID, even if in swagger it says "ID of pet"
        Response r = waitUntilOrderReadable(id, 5000);
        ResponseOrderGetSuccess body = r.as(ResponseOrderGetSuccess.class);

        System.out.println("\nStep 3: DELETE order by order id");
        deleteOrderWithRetry(id, 5000);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user deletes purchase order with success");
        waitUntilOrderDeleted(id, 5000);

        System.out.println("\nStep 4: RECHECK deleted order");
        orderActions.getOrderById(id, ResponseStatus.SC_NOT_FOUND, null);
        ExtentUtility.attachReportLog(ReportStep.INFO_STEP, "The user validates that the purchased order was deleted with success");
    }

    public void createOrder() {
        orderActions = new OrderActions();
        PropertyUtility propertyUtility = new PropertyUtility("requestData/createOrderData");
        this.expectedOrder = new RequestOrder(propertyUtility.getAllData());
        ResponseOrderSuccess responseOrderBody = orderActions.createNewOrder(expectedOrder);
        this.petId = expectedOrder.getPetId();
        this.id = responseOrderBody.getId();
        expectedOrder.setId(this.id);
    }

    private Response waitUntilOrderReadable(int orderId, long timeoutMs) {
        long start = System.currentTimeMillis();
        long delay = 150;
        Response last = null;
        while (System.currentTimeMillis() - start <= timeoutMs) {
            last = orderActions.getOrderByIdRaw(id);
            if (last.getStatusCode() == 200) return last;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
            delay = Math.min(1000, delay * 2);
        }
        Assert.fail("Order " + orderId + " not readable within " + timeoutMs + "ms. Last: "
                + (last == null ? "n/a" : last.getStatusCode()) + "\n" + (last == null ? "" : last.asString()));
        return last; // unreachable after fail
    }

    private void deleteOrderWithRetry(int orderId, long timeoutMs) {
        long start = System.currentTimeMillis();
        long delay = 150;

        while (System.currentTimeMillis() - start <= timeoutMs) {
            Response del = orderActions.deleteOrderByIdRaw(id); // or orderActions.deleteOrderByIdRaw(orderId)
            int sc = del.getStatusCode();

            if (sc == 200) {
                // success
                return;
            }

            if (sc == 404) {
                // confirm it's really gone
                Response get = orderActions.getOrderByIdRaw(id);
                if (get.getStatusCode() == 404) {
                    // already deleted (idempotent)
                    return;
                }
                // still exists on GET → try again after backoff
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
            delay = Math.min(1000, delay * 2);
        }

        Assert.fail("Could not delete order " + orderId + " within " + timeoutMs + "ms");
    }

    private void waitUntilOrderDeleted(int orderId, long timeoutMs) {
        long start = System.currentTimeMillis();
        long delay = 150;
        while (System.currentTimeMillis() - start <= timeoutMs) {
            Response get = orderActions.getOrderByIdRaw(id);
            if (get.getStatusCode() == 404) return;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
            delay = Math.min(1000, delay * 2);
        }
        Assert.fail("Order " + orderId + " did not become 404 within " + timeoutMs + "ms after delete");
    }

}
