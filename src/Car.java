public class Car {

    private int modelId;
    private String brand;
    private String model;
    private String type;
    private double pricePerDay;

    // Constructor
    public Car(int modelId, String brand, String model, String type, double pricePerDay) {
        this.modelId = modelId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.pricePerDay = pricePerDay;
    }

    //Getters and Setters
    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public String toString() {
    return String.format(
        "\t\t\t\t\t     | %-4d | %-10s | %-30s | %-20s | RM%-10.2f |",
        modelId, brand, model, type, pricePerDay);
}
    
}
