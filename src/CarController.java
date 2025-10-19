import java.util.List;
import java.util.Scanner;

public class CarController {
    

    Scanner scanner = new Scanner(System.in);
    CarDBA carDBA = new CarDBA();
    private String loggedIn; 

    public CarController(String loggedIn) { 
        this.loggedIn = loggedIn; 
    }

    public String displayCarBrand() {
        while (true) {
        System.out.println("\t\t\t\t\t==============================================");
        System.out.println("\t\t\t\t\t|     What car brand are you looking for?     |");
        System.out.println("\t\t\t\t\t==============================================");
        System.out.println("\t\t\t\t\t|                                             |");
        System.out.println("\t\t\t\t\t|           [0] Return to Main Menu           |");
        System.out.println("\t\t\t\t\t|           [1] Toyota                        |");
        System.out.println("\t\t\t\t\t|           [2] Lexus                         |");
        System.out.println("\t\t\t\t\t|           [3] BMW                           |");
        System.out.println("\t\t\t\t\t|           [4] Porsche                       |");
        System.out.println("\t\t\t\t\t|           [5] Mercedes                      |");
        System.out.println("\t\t\t\t\t|                                             |");
        System.out.println("\t\t\t\t\t==============================================");
        System.out.print  ("\t\t\t\t\tEnter your choice (0-5): ");

            if (scanner.hasNextInt()) {
                int carBrandChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                String carBrand;

                switch (carBrandChoice) {
                    case 0:
                        return null; // Return to main menu
                    case 1:
                        carBrand = "Toyota";
                        break;
                    case 2:
                        carBrand = "Lexus";
                        break;
                    case 3:
                        carBrand = "BMW";
                        break;
                    case 4:
                        carBrand = "Porsche";
                        break;
                    case 5:
                        carBrand = "Mercedes";
                        break;
                    default:
                        System.out.println("\t\t\t\t\tInvalid input! Please enter a number between 0 and 5.");
                        Main.anyKey(scanner);
                        continue;
                }
                return carBrand;

            } else {
                System.out.println("\t\t\t\t\tInvalid input! Please enter a number.");
                scanner.next(); // clear invalid input
                Main.anyKey(scanner);
            }
        }
    }

    public void displayCarModels(String brand) {
        List<Car> carModel = carDBA.getModelsByBrand(brand);
        System.out.println("\n\t\t\t\t\tAvailable " + brand + " models:");
        System.out.println("\t\t\t\t\t===================================================================================================");
        System.out.println("\t\t\t\t\t| ID   | Brand      | Model                         | Type                | Price/Day              |");
        System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");
        for (Car model : carModel) {
            System.out.println(model);
        }
        System.out.println("\t\t\t\t\t===================================================================================================");
    }

    public int selectCarModel() {
        System.out.print("\n\t\t\t\t\tPlease Select Your Car Model (Enter 0 to Return) :");

        if (scanner.hasNextInt()) {
            int modelID = scanner.nextInt();
            scanner.nextLine();
            return modelID;

        } else {
            System.out.println("\t\t\t\t\tInvalid input! Please enter a valid car model ID.");
            scanner.next();
            return 0;
        }
    }

    public void addNewCarModel() {

        String car_brand, car_model, car_type;
        double price;
        int stock;
    
        System.out.println("\n\t\t\t\t\t=== Add New Car Model ===");
        // Get brand
        while (true) {
            System.out.print("\t\t\t\t\tEnter the car brand: ");
            car_brand = scanner.nextLine().trim();
            if (car_brand.matches("[a-zA-Z ]+")) break;
            System.out.println("\t\t\t\t\tInvalid brand! Only letters and spaces allowed.");
        }
    
        // Get model
        while (true) {
            System.out.print("\t\t\t\t\tEnter the car model: ");
            car_model = scanner.nextLine().trim();
            if (car_model.matches("^[a-zA-Z\\\\d]+$")) break;
            System.out.println("\t\t\t\t\tInvalid model! Must contain at least one letter and one digit.");
        }

        // Get type
        while (true) {
            System.out.print("\t\t\t\t\tEnter the car type: ");
            car_type = scanner.nextLine().trim();
            if (car_type.matches(".*[a-zA-Z]+.*")) break;
            System.out.println("\t\t\t\t\tInvalid type! Must include at least one letter.");
        }
    
        // Get price
        while (true) {
            System.out.print("\t\t\t\t\tEnter the car price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                if (price > 0) break;
                System.out.println("\t\t\t\t\tInvalid price! Must be greater than 0.");
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Enter a number.");
                scanner.next(); // clear invalid
            }
        }
    
        // Get stock
        while (true) {
            System.out.print("\t\t\t\t\tEnter the car stock: ");
            if (scanner.hasNextInt()) {
                stock = scanner.nextInt();
                if (stock >= 0) break;
                System.out.println("\t\t\t\t\tInvalid stock! Must be 0 or more.");
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Enter a whole number.");
                scanner.next(); // clear invalid
            }
        }
    
        // Insert into DB
        boolean success = carDBA.insertNewCarModel(car_brand, car_model, car_type, price, stock);
        if (success) {
            System.out.println("\t\t\t\t\tCar model added successfully!");
        } else {
            System.out.println("\t\t\t\t\tFailed to add car model.");
        }
    
    }

    //staff update price 
    public void updatePrice() {
        System.out.println("\n\t\t\t\t\t=== Update Car Price ===");

        String carBrand = displayCarBrand();
        if (carBrand != null) {
        displayCarModels(carBrand);
        }

        int model_id;
        double price;

        // Step 2: Get model ID
        while (true) {
            System.out.print("\n\t\t\t\t\tEnter the Model ID you want to update: ");
            if (scanner.hasNextInt()) {
                model_id = scanner.nextInt();
                if (model_id > 0) {
                    break;
                } else {
                    System.out.println("\t\t\t\t\tInvalid ID! ID must be positive digits only.");
                }
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Please enter a number.");
                scanner.next();
            }
        }

        scanner.nextLine(); // consume newline

        // Step 3: Get new price
        while (true) {
            System.out.print("\t\t\t\t\tEnter the new car price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                if (price > 0) {
                    break;
                } else {
                    System.out.println("\t\t\t\t\tInvalid price! Price must be positive.");
                }
            } else {
                System.out.println("\t\t\t\t\tInvalid input! Please enter a valid number for price.");
                scanner.next();
            }
        }

        scanner.nextLine(); // consume newline

        // Step 4: Update price in DB
        carDBA.updateCarPrice(model_id, price);
    }
    
}

