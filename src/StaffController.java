import java.util.*;
import java.sql.*;

public class StaffController {
    private Scanner scanner = new Scanner(System.in);
    private final DBConnection DB = new DBConnection();
    StockController stockController = new StockController();
    RentalService rentalService = new RentalService();
    CarController carController = new CarController(null);
    CarDBA carDBA = new CarDBA();

    public void staffLogin() {
        String email, password;

        while (true) {
            Main.clearScreen();
            System.out.println("\t\t\t\t\t======================================");
            System.out.println("\t\t\t\t\t           STAFF LOGIN               ");
            System.out.println("\t\t\t\t\t======================================");

            // Get and validate email
            while (true) {
                System.out.print("\t\t\t\t\tEnter Email: ");
                email = scanner.nextLine().trim();
                if (email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) break;
                System.out.println("\t\t\t\t\tInvalid email format! Please try again.");
            }

            // Get and validate password
            while (true) {
                System.out.print("\t\t\t\t\tEnter Password: ");
                password = scanner.nextLine();
                if (!password.isEmpty()) break;
                System.out.println("\t\t\t\t\tPassword cannot be empty! Please try again.");
            }

            // Attempt login
            if (authenticateStaff(email, password)) {
                System.out.println("\t\t\t\t\tLogin successful! Redirecting to Staff Menu...");
                break;
            } else {
                System.out.println("\n\t\t\t\t\tInvalid email or password. Please try again.\n");
            }
        }
    }

    private boolean authenticateStaff(String email, String password) {
        String query = "SELECT * FROM staff WHERE email = ? AND password = ?";

        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String staffName = rs.getString("name");
                System.out.println("\n\t\t\t\t\tLogin successful! Welcome, " + staffName + ".");
                displayStaffMenu();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("\t\t\t\t\tDatabase error: " + e.getMessage());
        }
        return false;
    }

    public void displayStaffMenu() {
        int choice;

        while (true) {
            Main.clearScreen();
            System.out.println("\t\t\t\t\t================================================");
            System.out.println("\t\t\t\t\t|                  Staff Menu                  |");
            System.out.println("\t\t\t\t\t================================================");
            System.out.println("\t\t\t\t\t|                                              |");
            System.out.println("\t\t\t\t\t|      [1] Add a New Car Model                 |");
            System.out.println("\t\t\t\t\t|      [2] Add a New Car Model Stock           |");
            System.out.println("\t\t\t\t\t|      [3] Remove a Car                        |");
            System.out.println("\t\t\t\t\t|      [4] View All Cars                       |");
            System.out.println("\t\t\t\t\t|      [5] View Rental History                 |");
            System.out.println("\t\t\t\t\t|      [6] Update Car Price                    |");
            System.out.println("\t\t\t\t\t|      [7] Exit                                |");
            System.out.println("\t\t\t\t\t|                                              |");
            System.out.println("\t\t\t\t\t================================================");
            System.out.print("\t\t\t\t\tPlease enter your choice (1-7): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        Main.clearScreen();
                        carController.addNewCarModel();
                        Main.anyKey(scanner);
                        break;
                    case 2:
                        Main.clearScreen();
                        String carBrand = carController.displayCarBrand();
                        if (carBrand != null) {
                            carController.displayCarModels(carBrand);
                        }
                        stockController.addNewCarStock();
                        Main.anyKey(scanner);
                        break;
                    case 3:
                        Main.clearScreen();
                        stockController.removeCarStockModel();
                        Main.anyKey(scanner);
                        break;
                    case 4:
                        Main.clearScreen();
                        carBrand = carController.displayCarBrand();
                        if (carBrand != null) {
                            carDBA.displayCarModelStock(carBrand);
                        }
                        Main.anyKey(scanner);
                        break;
                    case 5:
                        Main.clearScreen();
                        rentalService.viewAllRentalHistory();
                        Main.anyKey(scanner);
                        break;
                    case 6:
                        Main.clearScreen();
                        carController.updatePrice();
                        Main.anyKey(scanner);
                        break;
                    case 7:
                        Main.clearScreen();
                        System.out.println("\t\t\t\t\tExiting the system... ");
                        System.out.println("\t\t\t\t\tThanks for using Ez4R Car Rental!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\t\t\t\t\tInvalid choice! Please enter a number between 1 and 7.");
                }
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Please enter a valid number.");
                scanner.next();
            }
        }
    }
}
