import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDBA {

    private final DBConnection DB = new DBConnection();

        public List<Car> getModelsByBrand(String brand) {
        List<Car> carModel = new ArrayList<>();
        String query = "SELECT model_id, car_brand, car_model, car_type, price FROM car_model WHERE car_brand = ? ORDER BY model_id ASC";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, brand);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("model_id");
                String model = rs.getString("car_model").trim();
                String type = rs.getString("car_type").trim();
                double price = rs.getDouble("price");

                carModel.add(new Car(id, brand, model, type, price));
            }
        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError retrieving car models: " + e.getMessage());
        }

        return carModel;
    }


    public String getAvailablePlateNumber(int modelId) {
        String sql = "SELECT plate_number FROM model_stock WHERE model_id = ? AND plate_number NOT IN (SELECT plate_number FROM rental)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, modelId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("plate_number");
        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError fetching plate number: " + e.getMessage());
        }
        return null;
    }

    public Car getCarModel(int modelId) {
        String sql = "SELECT car_brand, car_model, car_type, price FROM car_model WHERE model_id = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, modelId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Car(modelId, rs.getString("car_brand"), rs.getString("car_model"), rs.getString("car_type"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError fetching car model: " + e.getMessage());
        }
        return null;
    }
    
public void displayCarModelStock(String brand) {
    String selectQuery = "SELECT ms.plate_number, ms.car_model, ms.manufacture_year, cm.car_type, cm.price " +
                         "FROM model_stock ms " +
                         "JOIN car_model cm ON ms.car_model = cm.car_model " +
                         "WHERE ms.car_brand = ?";

    try (Connection connection = DB.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

        preparedStatement.setString(1, brand); // Bind the brand parameter
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("\t\t\t\t\tNo car models found for brand: " + brand);
            return;
        }

        System.out.println("\n\t\t\t\t\tAvailable car models for " + brand + ": ");
        System.out.println("\t\t\t\t\tPlate No.  | Car Model | Year | Car Type       | Price (RM)");
        System.out.println("\t\t\t\t\t----------------------------------------------------------------------");

        while (resultSet.next()) {
            String plate = resultSet.getString("plate_number");
            String model = resultSet.getString("car_model");
            int year = resultSet.getInt("manufacture_year");
            String type = resultSet.getString("car_type");
            double price = resultSet.getDouble("price");

            System.out.printf("\t\t\t\t\t%-10s | %-9s | %-4d | %-14s | %.2f%n",
                              plate, model, year, type, price);
        }

    } catch (SQLException e) {
        System.out.println("\t\t\t\t\tError retrieving car models: " + e.getMessage());
    }
}
    

    public boolean insertNewCarModel(String car_brand, String car_model, String car_type, double price, int stock) {
        String query = "INSERT INTO car_model (car_brand, car_model, car_type, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, car_brand);
            ps.setString(2, car_model);
            ps.setString(3, car_type);
            ps.setDouble(4, price);
            ps.setInt(5, stock);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError inserting new car model: " + e.getMessage());
            return false;
        }
    }
    
    public void updateCarPrice(int model_id, double price) {
        String updateQuery = "UPDATE car_model SET price = ? WHERE model_id = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, model_id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("\t\t\t\t\tCar price updated successfully!");
            } else {
                System.out.println("\t\t\t\t\tUpdate failed! No car found with the given Model ID.");
            }

        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError updating car price: " + e.getMessage());
        }
    }

}