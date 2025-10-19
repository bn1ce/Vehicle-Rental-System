import java.sql.*;

public class Rental {
    private int rentalId;
    private int custId;
    private String plateNumber;
    private Date startDate;
    private Date endDate;
    private double rentalCost;
    private String carBrand;
    private String carModel;
    private long rentalDays;
    
    public Rental(int custId, String plateNumber, Date startDate, Date endDate, double rentalCost, String carBrand, String carModel, long rentalDays) {
        this.custId = custId;
        this.plateNumber = plateNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalCost = rentalCost;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.rentalDays = rentalDays;
    }

    public Rental() {
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(long rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    
}
