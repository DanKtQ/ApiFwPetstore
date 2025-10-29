package objectData.pet;

import objectData.responseObject.ResponseNotNull;
import org.testng.Assert;

public class TagsObject implements ResponseNotNull {

    @Override
    public void validateNotNullFields() {
        Assert.assertNotNull(id);
        Assert.assertNotNull(name);
    }
//    @JsonProperty("id")
    private int id;

//    @JsonProperty("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
