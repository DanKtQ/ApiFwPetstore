package objectData.responseObject.responsePet;

import objectData.ResponseNotNull;
import objectData.pet.Category;
import objectData.pet.TagsObject;
import org.testng.Assert;

import java.util.List;

public class ResponsePetGetSuccess implements ResponseNotNull {

    @Override
    public void validateNotNullFields() {
        Assert.assertNotNull(id);
        Assert.assertNotNull(category);
        Assert.assertNotNull(name);
        Assert.assertNotNull(photoUrls);
        Assert.assertNotNull(status);
        for(TagsObject tagsObject:tags){
            tagsObject.validateNotNullFields();
        }
    }
    private int id;

    private Category category;

    protected String name;

    private List<String> photoUrls;

    private List <TagsObject> tags;

    private String status;

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public List<TagsObject> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public void setTags(List<TagsObject> tags) {
        this.tags = tags;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
