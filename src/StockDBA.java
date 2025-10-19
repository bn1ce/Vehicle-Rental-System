import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockDBA {

    private final DBConnection DB = new DBConnection();

    public void insertCarStock(String plate_number, int manufacture_year, int model_id) {
        String insertQuery = "INSERT INTO model_stock (plate_number, manufacture_year, car_brand, car_model, model_id) " +
                             "SELECT ?, ?, car_brand, car_model, model_id FROM car_model WHERE model_id = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, plate_number);
            preparedStatement.setInt(2, manufacture_year);
            preparedStatement.setInt(3, model_id);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("\t\t\t\t\tCar stock added successfully!");
            } else {
                System.out.println("\t\t\t\t\tFailed to add car stock. Please check the Model ID.");
            }

        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError inserting new car stock: " + e.getMessage());
        }
    }

    public void deleteCarStock(String plate_number) {
        String deleteQuery = "DELETE FROM model_stock WHERE plate_number = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, plate_number);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\t\t\t\t\tCar stock with plate number " + plate_number + " was successfully removed.");
            } else {
                System.out.println("\t\t\t\t\tNo record found with plate number: " + plate_number);
            }

        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError removing car stock: " + e.getMessage());
        }
    }

}
