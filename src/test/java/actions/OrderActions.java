package actions;

import io.restassured.response.Response;
import objectData.requestObject.RequestOrder;
import objectData.responseObject.responseStore.ResponseOrderGetFailed;
import objectData.responseObject.responseStore.ResponseOrderGetSuccess;
import objectData.responseObject.responseStore.ResponseOrderSuccess;
import org.testng.Assert;
import restClient.ResponseStatus;
import service.serviceImplementation.OrderServiceImpl;

public class OrderActions {

    private OrderServiceImpl orderServiceImpl;

    public OrderActions() {
        this.orderServiceImpl = new OrderServiceImpl();
    }

    public Response getOrderByIdRaw(int id) {
        return orderServiceImpl.getSpecificOrder(id);
    }

    public Response deleteOrderByIdRaw(int id) {
        return orderServiceImpl.deletePurchasedOrder(id);
    }

    public ResponseOrderSuccess createNewOrder(RequestOrder requestOrder) {
        Response response = orderServiceImpl.createOrder(requestOrder);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK, "Create order failed: " + response.asString());
        ResponseOrderSuccess body = response.as(ResponseOrderSuccess.class);
        body.validateNotNullFields();
        requestOrder.setId((int) body.getId());
        requestOrder.setPetId((int) body.getPetId());
        requestOrder.setQuantity(body.getQuantity());
        requestOrder.setStatus(body.getStatus());
        requestOrder.setComplete(body.isComplete());
        return body;
    }

    public void getOrderById(int id, int expectedStatus, RequestOrder expectedOrder) {
        Response response = orderServiceImpl.getSpecificOrder(id);
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, expectedStatus, "Unexpected status for GET /order/" + id + ". Body:\n" + response.asString());

        switch (expectedStatus) {
            case ResponseStatus.SC_OK: { // 200
                ResponseOrderGetSuccess body = response.as(ResponseOrderGetSuccess.class);
                body.validateNotNullFields();
                Assert.assertEquals(body.getId(), expectedOrder.getId(), "Body id must match requested id");
                Assert.assertEquals(body.getPetId(), expectedOrder.getPetId(), "Body petId must match requested petId");
                Assert.assertEquals(body.getQuantity(), expectedOrder.getQuantity(), "Body quantity must match requested quantity");
//                Assert.assertEquals(body.getShipDate(), expectedOrder.getShipDate(), "Body shipDate must match requested shipDate");
                Assert.assertEquals(body.getStatus(), expectedOrder.getStatus(), "Body status must match requested status");
                Assert.assertEquals(body.isComplete(), expectedOrder.isComplete(), "Body complete must match requested complete");
                break;
            }
            case ResponseStatus.SC_NOT_FOUND: { // 404
                ResponseOrderGetFailed err = response.as(ResponseOrderGetFailed.class);
                Assert.assertEquals(err.getMessage(), "Order not found", "Unexpected error message");
                Assert.assertEquals(err.getType(), "error");
                Assert.assertEquals(err.getCode(), 1);
                break;
            }
            case ResponseStatus.SC_INVALID: { // 400
                ResponseOrderGetFailed err = response.as(ResponseOrderGetFailed.class);
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

    public void deleteOrderById(int id) {
        Response response = orderServiceImpl.deletePurchasedOrder(id);
        Assert.assertEquals(response.getStatusCode(), ResponseStatus.SC_OK, "DELETE should be 200");
    }
}
