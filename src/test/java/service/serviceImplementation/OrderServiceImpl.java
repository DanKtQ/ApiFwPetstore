package service.serviceImplementation;

import io.restassured.response.Response;
import objectData.requestObject.RequestOrder;
import service.apiService.OrderApiService;
import service.interfaceService.OrderServiceInterface;

public class OrderServiceImpl implements OrderServiceInterface {

    // creating an instance of ApiService in order to access general methods(post, get.. from CommonApiService)
    private OrderApiService orderApiService;

    @Override
    public Response createOrder(RequestOrder body) {
        orderApiService = new OrderApiService();
        return orderApiService.post(body, "v2/store/order");
    }

    @Override
    public Response getSpecificOrder(int id) {
        orderApiService = new OrderApiService();
        String url = "v2/store/order/" + id;
        return orderApiService.getOrder(url);
    }

    @Override
    public Response deletePurchasedOrder(int id) {
        orderApiService = new OrderApiService();
        String url = "v2/store/order/" + id;
        return orderApiService.deleteOrder(url);
    }
}
