package objectData.requestObject;

import objectData.requestPreparation;
import objectData.responseObject.responseUser.ResponseUserGetSuccess;

import java.util.HashMap;
import java.util.Random;

public class RequestUser extends ResponseUserGetSuccess implements requestPreparation {

    public RequestUser(HashMap<String, String> testData) {
        prepareObject(testData);
    }

    @Override
    public void prepareObject(HashMap<String, String> testData) {
        for (String key : testData.keySet()) {
            switch (key) {
                case "id":
                    setId(Integer.parseInt(testData.get(key)));
                    break;
                case "username":
                    setUsername(testData.get(key));
                    break;
                case "firstName":
                    setFirstName(testData.get(key));
                    break;
                case "lastName":
                    setLastName(testData.get(key));
                    break;
                case "email":
                    setEmail(testData.get(key));
                    break;
                case "password":
                    setPassword(testData.get(key));
                    break;
                case "phone":
                    setPhone(testData.get(key));
                    break;
                case "userStatus":
                    setUserStatus(Integer.parseInt(testData.get(key)));
                    break;
            }
        }
        adjustObjectVariable();
    }

    private void adjustObjectVariable() {
        Random random = new Random();
        id = random.nextInt(1_000_000_000); // random int between 0 and 999,999,999
    }

}
