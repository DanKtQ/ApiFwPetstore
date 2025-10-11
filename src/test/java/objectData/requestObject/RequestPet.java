package objectData.requestObject;

import objectData.pet.Category;
import objectData.requestPreparation;
import objectData.responseObject.ResponsePetSuccess;
import objectData.pet.TagsObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestPet extends ResponsePetSuccess implements requestPreparation {

    public RequestPet(HashMap<String, String> testData) {
        prepareObject(testData);
    }

    @Override
    public void prepareObject(HashMap<String, String> testData) {
        // id
        if (testData.containsKey("id")) {
            setId(Long.parseLong(testData.get("id")));
        }

        // category
        Category category = new Category();
        if (testData.containsKey("category.id")) {
            category.setId(Long.parseLong(testData.get("category.id")));
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
                    t.setId(Long.parseLong(pieces[0].trim()));
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
    }

}
