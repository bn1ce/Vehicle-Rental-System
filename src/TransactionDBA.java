import java.sql.*;

public class TransactionDBA {
    
    private final DBConnection DB = new DBConnection();

    public int saveTransaction(int rentalId, double totalCost, String paymentMethod) {
        String sql = "INSERT INTO transaction (rental_id, total_cost, payment_method, transaction_date, transaction_time) VALUES (?, ?, ?, CURDATE(), CURTIME())";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, rentalId);
            stmt.setDouble(2, totalCost);
            stmt.setString(3, paymentMethod);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
        return -1;
    }
}