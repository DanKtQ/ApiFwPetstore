package objectData;

import java.util.HashMap;

public interface requestPreparation {

    //the scope of this interface is to serialize a specific request body
    void prepareObject(HashMap<String, String> testData);

}
