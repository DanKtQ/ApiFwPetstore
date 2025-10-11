package objectData.responseObject;

import objectData.pet.Category;
import objectData.pet.TagsObject;

import java.util.List;

public class ResponsePetGetSuccess {

    private long id;

    private Category category;

    protected String name;

    private List<String> photoUrls;

    private List <TagsObject> tags;

    private String status;

    public long getId() {
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

    public void setId(long id) {
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
