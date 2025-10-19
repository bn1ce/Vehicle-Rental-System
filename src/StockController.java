import java.util.Scanner;

public class StockController {

    Scanner scanner = new Scanner(System.in);
    StockDBA stockDBA = new StockDBA(); 
    CarController carController = new CarController(null);
    CarDBA carDBA = new CarDBA();

    public void addNewCarStock() {
        String plate_number;
        int manufacture_year;
        int model_id;

        System.out.println("\n\t\t\t\t\t=== Add New Car Stock ===");

        while (true) {
            System.out.print("\t\t\t\t\tEnter the Model ID you want to add: ");
            if (scanner.hasNextInt()) {
                model_id = scanner.nextInt();
                if (validId(model_id)) {
                    break;
                } else {
                    System.out.println("\t\t\t\t\tInvalid Model ID!");
                }
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Enter a number.");
                scanner.next(); // clear invalid input
            }
        }
        
        while (true) {
            scanner.nextLine(); // clear buffer
            System.out.print("\t\t\t\t\tEnter the plate number: ");
            plate_number = scanner.nextLine();
            if (validplate_number(plate_number)) {
                break;
            } else {
                System.out.println("\t\t\t\t\tInvalid plate number! Example format: ABC 1234");
            }
        }

        while (true) {
            System.out.print("\t\t\t\t\tEnter the manufacture year: ");
            if (scanner.hasNextInt()) {
                manufacture_year = scanner.nextInt();
                if (validYear(manufacture_year)) {
                    break;
                } else {
                    System.out.println("\t\t\t\t\tInvalid manufacture year! Must be between 1980 and 2030.");
                }
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Enter a number.");
                scanner.next(); // clear wrong input
            }
        }

        // Insert into the database
        stockDBA.insertCarStock(plate_number, manufacture_year, model_id);
    }

    public void removeCarStockModel() {

        System.out.println("\n\t\t\t\t\t=== Remove Car Stock ===");
        String carBrand = carController.displayCarBrand();
        if (carBrand != null) {
        carDBA.displayCarModelStock(carBrand);
        }
    
        String plate_number;
        while (true) {
            System.out.print("\t\t\t\t\tEnter the plate number: ");
            scanner.nextLine(); // clear buffer
            plate_number = scanner.nextLine();
            if (validplate_number(plate_number)) {
                break;
            } else {
                System.out.println("\t\t\t\t\tInvalid plate number! Format should be like ABC 1234");
            }
        }
    
        stockDBA.deleteCarStock(plate_number);      
        
    }
    

    // Validate alphanumeric plate number with optional spaces
    public boolean validplate_number(String plate_number) {
        // Allows letters, digits, spaces, and hyphens
            return plate_number.matches("^[A-Za-z]+\\s[0-9]+$");
    }

    public boolean validYear(int year) {
        return year >= 1980 && year <= 2030;
    }

    public boolean validId(int model_id) {
        return model_id > 0;
    }
}