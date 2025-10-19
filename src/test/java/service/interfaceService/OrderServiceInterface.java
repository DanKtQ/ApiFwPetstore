package service.interfaceService;

import io.restassured.response.Response;
import objectData.requestObject.RequestOrder;

public interface OrderServiceInterface {

    // this interface represents the actions which we want to do with a module(Store)

    Response createOrder(RequestOrder body);

    Response getSpecificOrder(int petId);

    Response deletePurchasedOrder(int id);
}
