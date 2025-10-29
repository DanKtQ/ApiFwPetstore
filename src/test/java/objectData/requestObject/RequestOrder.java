package objectData.requestObject;

import objectData.responseObject.responseStore.ResponseOrderSuccess;

import java.util.HashMap;
import java.util.Random;

public class RequestOrder extends ResponseOrderSuccess implements requestPreparation {

    public RequestOrder(HashMap<String, String> testData) {
        prepareObject(testData);
    }

    @Override
    public void prepareObject(HashMap<String, String> testData) {
        for (String key : testData.keySet()) {
            switch (key) {
                case "id":
                    setId(Integer.parseInt(testData.get(key)));
                    break;
                case "petId":
                    setPetId(Integer.parseInt(testData.get(key)));
                    break;
                case "quantity":
                    setQuantity(Integer.parseInt(testData.get(key)));
                    break;
                case "shipDate":
                    setShipDate(testData.get(key));
                    break;
                case "status":
                    setStatus(testData.get(key));
                    break;
                case "complete":
                    setComplete(Boolean.parseBoolean(testData.get(key)));
                    break;
            }
        }
        adjustObjectVariable();
    }

    private void adjustObjectVariable(){
        Random random = new Random();
        id = random.nextInt(1_000_000_000); // random int between 0 and 999,999,999
    }

}
