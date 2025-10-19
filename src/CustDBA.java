import java.sql.*;

public class CustDBA {

    private final DBConnection DB = new DBConnection();

    public boolean saveCustomer(Customer cust) {
        String query = "INSERT INTO customer (name, email, contact, password, license_no) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, cust.getName());
            ps.setString(2, cust.getEmail());
            ps.setString(3, cust.getContact());
            ps.setString(4, cust.getPassword());
            ps.setString(5, cust.getLicenseNo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error saving customer: " + e.getMessage());
            return false;
        }
    }

    public boolean checkLogin(String email, String password) {
        String query = "SELECT * FROM customer WHERE email = ? AND password = ?";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String custName = rs.getString("name");
                System.out.println("\n\t\t\t\t\tLogin successful! Welcome, " + custName + ".");
                return true;
            } else {
                System.out.println("\t\t\t\t\tInvalid email or password.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("\t\t\t\t\tLogin error: " + e.getMessage());
            return false;
        }
    }

    public int getCustomerIdByEmail(String email) {
        String sql = "SELECT cust_id FROM customer WHERE email = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("cust_id");
            }

        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tError fetching customer ID: " + e.getMessage());
        }
        return -1;
    }

}
