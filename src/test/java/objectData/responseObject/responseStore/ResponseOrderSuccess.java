package objectData.responseObject.responseStore;

import objectData.responseObject.ResponseNotNull;
import org.testng.Assert;

public class ResponseOrderSuccess implements ResponseNotNull {

    @Override
    public void validateNotNullFields() {
        Assert.assertNotNull(id);
        Assert.assertNotNull(petId);
        Assert.assertNotNull(quantity);
        Assert.assertNotNull(shipDate);
        Assert.assertNotNull(status);
        Assert.assertNotNull(complete);
    }

    protected int id;

    private int petId;

    private int quantity;

    private String shipDate;

    private String status;

    private boolean complete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
