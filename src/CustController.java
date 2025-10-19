
import java.util.Scanner;

public class CustController {

    Scanner scanner = new Scanner(System.in);
    private String loggedIn;
    RentalService rentalService = new RentalService();
    CarController carController;
    CustDBA custDBA = new CustDBA();

        public boolean custLoginRegister() {
        while (true) {
            Main.clearScreen();
            System.out.println("\t\t\t\t\t==============================================");
            System.out.println("\t\t\t\t\t              Register / Login                ");
            System.out.println("\t\t\t\t\t==============================================");
            System.out.println("\t\t\t\t\t|                                            |");
            System.out.println("\t\t\t\t\t|        [0] Return to Previous Page         |");
            System.out.println("\t\t\t\t\t|        [1] Register                        |");
            System.out.println("\t\t\t\t\t|        [2] Login                           |");
            System.out.println("\t\t\t\t\t|                                            |");
            System.out.println("\t\t\t\t\t==============================================");
            System.out.println(" ");
            System.out.print ("\t\t\t\t\tPlease choose an option (0-2): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        return false;
                    case 1:
                        custRegister();
                        Main.clearScreen();
                        String email = custLogin();
                        if (email != null) {
                            loggedIn = email;
                            carController = new CarController(loggedIn);
                            return true;
                        } else {
                            return false;
                        }
                    case 2:
                        Main.clearScreen();
                        email = custLogin();
                        if (email != null) {
                            loggedIn = email;
                            carController = new CarController(loggedIn);
                            displayCustomerMenu();
                            return true;
                        } else {
                            return false;
                        }
                    default:
                        System.out.println("\t\t\t\t\tInvalid input! Please enter 0, 1 or 2.");
                        Main.anyKey(scanner);
                }
            } else {
                scanner.nextLine();
                System.out.println("\t\t\t\t\tInvalid input! Please enter a number.");
                Main.anyKey(scanner);
            }
        }
    }

        
    public void displayCustomerMenu() {
        int choice;
        do {
            Main.clearScreen();
System.out.println("\t\t\t\t\t==============================================");
System.out.println("\t\t\t\t\t|                Customer Menu               |");
System.out.println("\t\t\t\t\t==============================================");
System.out.println("\t\t\t\t\t|                                             |");
System.out.println("\t\t\t\t\t|           [1] Rent a Car                    |");
System.out.println("\t\t\t\t\t|           [2] Cancel Rental                 |");
System.out.println("\t\t\t\t\t|           [3] Car Listing                   |");
System.out.println("\t\t\t\t\t|           [4] View Rental History           |");
System.out.println("\t\t\t\t\t|           [5] Exit                          |");
System.out.println("\t\t\t\t\t|                                             |");
System.out.println("\t\t\t\t\t==============================================");
System.out.print("\t\t\t\t\tPlease enter your choice (1-4): ");

            while (!scanner.hasNextInt()) {
                System.out.println("\t\t\t\t\tInvalid input! Please enter a number.");
                scanner.next();
                Main.anyKey(scanner);
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Main.clearScreen();
                    String carBrand = carController.displayCarBrand();
                    if (carBrand != null) {
                        Main.clearScreen();
                        carController.displayCarModels(carBrand);
                        int modelId = carController.selectCarModel();
                        if (modelId != 0 && loggedIn != null) {
                            rentalService.processRental(modelId, loggedIn);
                        }
                    }
                    Main.anyKey(scanner);
                    break;
                case 2:
                    Main.clearScreen();
                    rentalService.cancelRental(loggedIn);
                    Main.anyKey(scanner);
                    break;
                case 3:
                    Main.clearScreen();
                    carBrand = carController.displayCarBrand();
                    if (carBrand != null) {
                        Main.clearScreen();
                        carController.displayCarModels(carBrand);
                    }
                    Main.anyKey(scanner);
                    break;
                case 4:
                    Main.clearScreen();
                    rentalService.viewRentalHistory(loggedIn);
                    Main.anyKey(scanner);
                    break;
                case 5:
                    Main.clearScreen();
                    System.out.println("\t\t\t\t\tExiting the system... ");
                    System.out.println("\t\t\t\t\tThanks for using Ez4R Car Rental!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\t\t\t\t\tInvalid choice!");
                    Main.clearScreen();
                    Main.anyKey(scanner);
                    
            }
        } while (true);
    }


    
    // CUSTOMER REGISTER
    public void custRegister() {
        System.out.println("\t\t\t\t\tRegistration Page");

        String name;
        while (true) {
            System.out.print("\t\t\t\t\tName: ");
            name = scanner.nextLine();
            if (validName(name)) break;
            System.out.println("\t\t\t\t\tInvalid name! Only letters and spaces allowed.");
        }

        String email;
        while (true) {
            System.out.print("\t\t\t\t\tEmail: ");
            email = scanner.nextLine();
            if (validEmail(email)) break;
            System.out.println("\t\t\t\t\tInvalid email! Must contain '@' and end with '.com'.");
        }

        String contact;
        while (true) {
            System.out.print("\t\t\t\t\tContact Number: ");
            contact = scanner.nextLine();
            if (validContact(contact)) break;
            System.out.println("\t\t\t\t\tInvalid contact number! Must start with 0 and be 10 or 11 digits.");
        }

        System.out.print("\t\t\t\t\tPassword: ");
        String password = scanner.nextLine();

        String licenseNo;
        while (true) {
            System.out.print("\t\t\t\t\tLicense Number: ");
            licenseNo = scanner.nextLine();
            if (validLicenseNo(licenseNo)) break;
            System.out.println("\t\t\t\t\tInvalid license number! Must be 8 characters with letters and numbers.");
        }

        Customer cust = new Customer(name, contact, email,  password, licenseNo);
        if (custDBA.saveCustomer(cust)) {
            System.out.println("\t\t\t\t\tRegistration successful!");
        } else {
            System.out.println("\t\t\t\t\tRegistration failed.");
        }
    }

    
    
    // CUSTOMER LOGIN
    public String custLogin() {
    System.out.println("\t\t\t\t\tLogin Page");

    while (true) {
        System.out.print("\t\t\t\t\tEmail: ");
        String email = scanner.nextLine();

        System.out.print("\t\t\t\t\tPassword: ");
        String password = scanner.nextLine();  // use scanner instead of console

        if (custDBA.checkLogin(email, password)) {
            return email;
        } else {
            System.out.println("\t\t\t\t\tInvalid email or password. Try again.");
        }
    }
}
    
    public boolean validName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    public boolean validEmail(String email) {
        return email.matches("^[^\\s@]+@[^\\s@]+\\.com$");
    }

    public boolean validContact(String contact) {
        return contact.matches("^0\\d{9,10}$");
    }

    public boolean validLicenseNo(String licenseNo) {
        return licenseNo.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8}$");
    }
    
    
}