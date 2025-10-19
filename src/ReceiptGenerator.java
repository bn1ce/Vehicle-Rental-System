import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ReceiptGenerator {

    private final DBConnection DB = new DBConnection();
    Scanner scanner = new Scanner(System.in);
    CustController custController = new CustController();

    public void printReceipt(int transactionID, String email) {

        CarDBA carDBA = new CarDBA();
        RentalDBA rentalDBA = new RentalDBA();
        CustDBA custDBA = new CustDBA();

        // Step 1: Get customer ID by email
        int custID = custDBA.getCustomerIdByEmail(email);
        if (custID == -1) {
            System.out.println("No customer found.");
            return;
        }

        // Step 2: Get rental info from RentalDBA
        Rental rental = rentalDBA.getRentalInfo(custID);
        if (rental == null) {
            System.out.println("\t\t\t\t\tNo rental found for customer.");
            return;
        }

        // Step 3: Get model info from model_stock
        String modelId;
        String manufactureYear;

        try (Connection conn = DB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT model_id, manufacture_year FROM model_stock WHERE plate_number = ?")) {
            stmt.setString(1, rental.getPlateNumber());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                modelId = rs.getString("model_id");
                manufactureYear = rs.getString("manufacture_year");
            } else {
                System.out.println("\t\t\t\t\tModel stock not found.");
                return;
            }
        } catch (Exception e) {
            System.out.println("\t\t\t\t\tError getting model stock: " + e.getMessage());
            return;
        }

        // Step 4: Get car model details using CarDBA
        Car car = carDBA.getCarModel(Integer.parseInt(modelId));
        if (car == null) {
            System.out.println("\t\t\t\t\tCar model not found.");
            return;
        }

        // === Receipt Output ===
        Receipt receipt = new Receipt(rental, car, manufactureYear, getCurrentTime());
        System.out.println(receipt);
        
    }

    // Get the current time in the required format
    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

}
