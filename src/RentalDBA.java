import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDBA {
    
    private final DBConnection DB = new DBConnection();

    public int saveRental(Rental rental) {
        String sql = "INSERT INTO rental (cust_id, plate_number, start_date, end_date, rental_cost, car_brand, car_model, rental_days) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, rental.getCustId());
            stmt.setString(2, rental.getPlateNumber());
            stmt.setDate(3, rental.getStartDate());
            stmt.setDate(4, rental.getEndDate());
            stmt.setDouble(5, rental.getRentalCost());
            stmt.setString(6, rental.getCarBrand());
            stmt.setString(7, rental.getCarModel());
            stmt.setLong(8, rental.getRentalDays());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error saving rental: " + e.getMessage());
        }
        return -1;
    } 

    public Rental getRentalInfo(int custID) {
        String sql = "SELECT * FROM rental WHERE cust_id = ? ORDER BY rental_id DESC LIMIT 1";
    
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, custID);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                Rental rental = new Rental();
                rental.setRentalId(rs.getInt("rental_id"));
                rental.setCustId(custID);
                rental.setPlateNumber(rs.getString("plate_number"));
                rental.setStartDate(rs.getDate("start_date"));
                rental.setEndDate(rs.getDate("end_date"));
                rental.setRentalCost(rs.getDouble("rental_cost"));
                rental.setCarBrand(rs.getString("car_brand"));
                rental.setCarModel(rs.getString("car_model"));
                rental.setRentalDays(rs.getInt("rental_days"));
                return rental;
            }
        } catch (SQLException e) {
            System.out.println("Error getting rental: " + e.getMessage());
        }
        return null;
    }

public List<Rental> getRentalHistory(int custID) {
    List<Rental> rentalHistory = new ArrayList<>();
    String sql = "SELECT * FROM rental WHERE cust_id = ? ORDER BY rental_id ASC";

    try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, custID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Rental rental = new Rental();
            rental.setRentalId(rs.getInt("rental_id"));
            rental.setCustId(custID);
            rental.setPlateNumber(rs.getString("plate_number"));
            rental.setStartDate(rs.getDate("start_date"));
            rental.setEndDate(rs.getDate("end_date"));
            rental.setRentalCost(rs.getDouble("rental_cost"));
            rental.setCarBrand(rs.getString("car_brand"));
            rental.setCarModel(rs.getString("car_model"));
            rental.setRentalDays(rs.getInt("rental_days"));
            
            rentalHistory.add(rental); // <- Add each rental to the list
        }
    } catch (SQLException e) {
        System.out.println("Error getting rental history: " + e.getMessage());
    }
        return rentalHistory; // return list (could be empty if no rentals)
    }

    public boolean deleteRental(int rentalId) {
        String sql = "DELETE FROM rental WHERE rental_id = ?";

        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rentalId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
        System.out.println("Error deleting rental: " + e.getMessage());
        }

        return false;
    }  
    
    public List<Rental> getAllRentalHistory() {
    List<Rental> rentalHistory = new ArrayList<>();
    String sql = "SELECT * FROM rental ORDER BY rental_id ASC";

    try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Rental rental = new Rental();
            rental.setRentalId(rs.getInt("rental_id"));
            rental.setCustId(rs.getInt("cust_id"));
            rental.setPlateNumber(rs.getString("plate_number"));
            rental.setStartDate(rs.getDate("start_date"));
            rental.setEndDate(rs.getDate("end_date"));
            rental.setRentalCost(rs.getDouble("rental_cost"));
            rental.setCarBrand(rs.getString("car_brand"));
            rental.setCarModel(rs.getString("car_model"));
            rental.setRentalDays(rs.getInt("rental_days"));
            
            rentalHistory.add(rental); // <- Add each rental to the list
        }
    } catch (SQLException e) {
        System.out.println("Error getting rental history: " + e.getMessage());
    }
        return rentalHistory; // return list (could be empty if no rentals)
    }
    

}
