import java.sql.Date;
import java.util.*;

public class RentalService {

    CarDBA carDBA = new CarDBA();
    CustDBA customerDBA = new CustDBA();
    RentalDBA rentalDBA = new RentalDBA();
    TransactionDBA transactionDBA = new TransactionDBA();
    PaymentService paymentService = new PaymentService();
    Scanner scanner = new Scanner(System.in);

    public void processRental(int modelId, String loggedIn) {
        if (loggedIn == null) {
            System.out.println("\t\t\t\t\tNo user is logged in.");
            return;
        }
        Main.clearScreen();
        System.out.print("\t\t\t\t\tPlease Select Your Rental Date ");
        System.out.print("\n\t\t\t\t\tExample ('2025-XX-XX') ");
        System.out.print("\n\t\t\t\t\tEnter start date (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine();
        System.out.print("\t\t\t\t\tEnter end date (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine();

        try {
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);
            long rentalDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);

            System.out.println("\t\t\t\t\tRental Days: " + rentalDays);

            if (rentalDays < 1) {
                System.out.println("\t\t\t\t\tEnd date cannot be before start date!");
                return;
            }

            String plateNumber = carDBA.getAvailablePlateNumber(modelId);
            if (plateNumber == null) {
                System.out.println("\t\t\t\t\tNo available car for this model.");
                return;
            }

            Car car = carDBA.getCarModel(modelId);
            if (car == null) {
                System.out.println("\t\t\t\t\tError: Car model not found for model ID: " + modelId);
                return;
            }

            double totalCost = rentalDays * car.getPricePerDay();
            
            Main.clearScreen();
            System.out.println("\n\t\t\t\t\tSelected Car: " + car.getBrand() + " " + car.getModel());
            System.out.println("\t\t\t\t\tPlate Number: " + plateNumber);
            System.out.println("\t\t\t\t\tRental Period: " + startDateStr + " - " + endDateStr);
            System.out.println("\t\t\t\t\tTotal Rental Price: " + totalCost);
            System.out.print("\t\t\t\t\tContinue to payment? (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            scanner.nextLine();

            if (Character.toLowerCase(confirmation) != 'y') {
                System.out.println("\t\t\t\t\tCar rental canceled.");
                return;
            }

            int custId = customerDBA.getCustomerIdByEmail(loggedIn);
            if (custId == -1) {
                System.out.println("\t\t\t\t\tNo customer found.");
                return;
            }

            Main.clearScreen();
            String paymentMethod = paymentService.paymentMethod();

            Rental rental = new Rental(custId, plateNumber, startDate, endDate, totalCost, car.getBrand(), car.getModel(), rentalDays);
            int rentalId = rentalDBA.saveRental(rental);

            int transactionId = transactionDBA.saveTransaction(rentalId, totalCost, paymentMethod);
            
            Main.clearScreen();
            ReceiptGenerator receiptGenerator = new ReceiptGenerator();
            receiptGenerator.printReceipt(transactionId, loggedIn);

        } catch (IllegalArgumentException e) {
            System.out.println("\t\t\t\t\tInvalid date format! Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("\t\t\t\t\tError: " + e.getMessage());
        }
    }

    public void viewRentalHistory(String loggedIn) {
    
        // Get customer id
        int custId = customerDBA.getCustomerIdByEmail(loggedIn);
        if (custId == -1) {
            System.out.println("\t\t\t\t\tError: Customer not found.");
            return;
        }
    
        // Get rental history
        List<Rental> rentals = rentalDBA.getRentalHistory(custId);
    
        if (rentals.isEmpty()) {
            System.out.println("\t\t\t\t\tNo rental history found.");
            return;
        }
        Main.clearScreen();

System.out.println("     ===============================================================================================================================");
System.out.println("     |                                             Your Rental History                                                             |");
System.out.println("     ===============================================================================================================================");
System.out.println("     | ID  |   Start Date   |    End Date    |     Cost     |   Brand   |               Model               |  Days Rented |");
System.out.println("     -------------------------------------------------------------------------------------------------------------------------------");

for (Rental rental : rentals) {
    System.out.printf("     | %-3d | %-14s | %-14s | %-12.2f | %-9s | %-34s | %-12s |\n",
            rental.getRentalId(),
            rental.getStartDate(),
            rental.getEndDate(),
            rental.getRentalCost(),
            rental.getCarBrand(),
            rental.getCarModel(),
            rental.getRentalDays());
}

System.out.println("     ===============================================================================================================================");
    }


    public void cancelRental(String loggedIn) {
        viewRentalHistory(loggedIn); 
    
        int custId = customerDBA.getCustomerIdByEmail(loggedIn);
        if (custId == -1) {
            System.out.println("\t\t\t\t\tError: Customer not found.");
            return;
        }
    
        System.out.print("\n\t\t\t\t\tEnter the Rental ID to cancel (or 0 to return): ");
        if (!scanner.hasNextInt()) {
            System.out.println("\t\t\t\t\tInvalid input! Please enter a valid Rental ID.");
            scanner.next(); // Clear invalid input
            return;
        }
    
        int rentalId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        if (rentalId == 0) {
            System.out.println("\t\t\t\t\tReturning to menu...");
            return;
        }
    
        boolean success = rentalDBA.deleteRental(rentalId);
        if (success) {
            System.out.println("\t\t\t\t\tRental successfully canceled.");
        } else {
            System.out.println("\t\t\t\t\tError: No matching rental found or already canceled.");
        }
    }

    public void viewAllRentalHistory() {
    List<Rental> rentals = rentalDBA.getAllRentalHistory();

    if (rentals.isEmpty()) {
        System.out.println("\t\t\t\t\tNo rental history found.");
        return;
    }

    System.out.println("\t\t\t\t\t========================================================================================================================");
    System.out.println("\t\t\t\t\t                                              ALL RENTAL HISTORY                         ");
    System.out.println("\t\t\t\t\t========================================================================================================================");
    System.out.printf("\t\t\t\t\t%-10s | %-12s | %-12s | %-12s | %-10s | %-12s | %-20s | %-12s\n",
            "Rental ID", "Customer ID", "Start Date", "End Date", "Total Cost", "Car Brand", "Car Model", "Rental Days");
    System.out.println("\t\t\t\t\t------------------------------------------------------------------------------------------------------------------------");

    for (Rental rental : rentals) {
        System.out.printf("\t\t\t\t\t%-10d | %-12d | %-12s | %-12s | RM%-8.2f | %-12s | %-20s | %-12s\n",
                rental.getRentalId(),
                rental.getCustId(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getRentalCost(),
                rental.getCarBrand(),
                rental.getCarModel(),
                rental.getRentalDays());
    }

    System.out.println("\t\t\t\t\t========================================================================================================================");
}

    
}