package objectData.pet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
