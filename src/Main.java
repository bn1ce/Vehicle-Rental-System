import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CustController custController = new CustController();
        StaffController staffController = new StaffController();
        int custOrStaff;

        do {
            
            clearScreen();
System.out.println("\t\t\t\t\t (o o)                                 (o o)");
System.out.println("\t\t\t\t\t(  V  )        \u001B[35mWelcome To Ez4R\u001B[0m        (  V  )");
System.out.println("\t\t\t\t\t              \u001B[35mCar Rental System\u001B[0m");
System.out.println("\t\t\t\t\t--m-m-----------------------------------m-m--");

System.out.println("\t\t\t\t\t|                                            |");
System.out.println("\t\t\t\t\t|           [1] Customer                     |");
System.out.println("\t\t\t\t\t|           [2] Staff                        |");
System.out.println("\t\t\t\t\t|           [0] Exit                         |");
System.out.println("\t\t\t\t\t|                                            |");
System.out.println("\t\t\t\t\t==============================================");

        
            System.out.println(" ");
            System.out.print("\t\t\t\t\tPlease Enter A Digit 0-2: ");
            while (!scanner.hasNextInt()) {
                System.out.println("\t\t\t\t\tInvalid input! Please enter a valid number (0, 1, or 2).");
                System.out.print("\t\t\t\t\tPlease Enter A Digit 0-2: ");
                scanner.next(); 
            }

            custOrStaff = scanner.nextInt();
            scanner.nextLine(); 

            switch (custOrStaff) {
                case 1:
                    Main.clearScreen();
                    custController.custLoginRegister();
                    custController.displayCustomerMenu();
                    break;
                case 2:
                    Main.clearScreen();
                    staffController.staffLogin();
                    break;
                case 0:
                    System.out.println("\t\t\t\t\tThank you for using Ez4R Car Rental. Goodbye!");
                    break;
                default:
                    System.out.println("\t\t\t\t\tInvalid Input. Please Enter 0, 1, or 2.");
                    anyKey(scanner); 
                    break;
            }

        } while (custOrStaff != 0);

        scanner.close();
    }
    
    public static void anyKey(Scanner scanner) {
    System.out.println("\u001B[32m\t\t\t\t\tPress any key to continue...\u001B[0m");
    scanner.nextLine();
}
    
    public static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}