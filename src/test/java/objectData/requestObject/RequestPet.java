package objectData.requestObject;

import objectData.pet.Category;
import objectData.responseObject.responsePet.ResponsePetSuccess;
import objectData.pet.TagsObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RequestPet extends ResponsePetSuccess implements requestPreparation {

    public RequestPet(HashMap<String, String> testData) {
        prepareObject(testData);
    }

    @Override
    public void prepareObject(HashMap<String, String> testData) {
        // id
        if (testData.containsKey("id")) {
            setId(Integer.parseInt(testData.get("id")));
        }

        // category
        Category category = new Category();
        if (testData.containsKey("category.id")) {
            category.setId(Integer.parseInt(testData.get("category.id")));
        }
        if (testData.containsKey("category.name")){
            category.setName(testData.get("category.name"));
        }
        setCategory(category);

        // name
        if (testData.containsKey("name")){
            setName(testData.get("name"));
        }

        // photoUrls
        if (testData.containsKey("photoUrls")){
            String raw = testData.get("photoUrls");
            List<String> urls = new ArrayList<>();
            for (String s : raw.split(",")) {
                String trimmed = s.trim();
                if (!trimmed.isEmpty()) urls.add(trimmed);
            }
            setPhotoUrls(urls);
        }

        // tags (comma-separated id:name)
        if (testData.containsKey("tags")) {
            String raw = testData.get("tags");
            List<TagsObject> tagList = new ArrayList<>();
            for (String part : raw.split(",")) {
                String item = part.trim();
                if (item.isEmpty()) continue;
                String[] pieces = item.split(":", 2);
                TagsObject t = new TagsObject();
                if (pieces.length > 0 && !pieces[0].trim().isEmpty()) {
                    t.setId(Integer.parseInt(pieces[0].trim()));
                }
                if (pieces.length > 1) {
                    t.setName(pieces[1].trim());
                }
                tagList.add(t);
            }
            setTags(tagList);
        }

        // status
        if (testData.containsKey("status")) {
            setStatus(testData.get("status"));
        }

        adjustObjectVariable();
    }

    public void adjustObjectVariable(){
        name = name + System.currentTimeMillis();
        Random random = new Random();
        id = random.nextInt(1_000_000_000); // random int between 0 and 999,999,999
    }

}
