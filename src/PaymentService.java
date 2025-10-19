import java.util.Scanner;

public class PaymentService {

    Scanner scanner = new Scanner(System.in);

    public String paymentMethod() {
        int paymentChoice;
        System.out.println("\t\t\t\t\t==============================================");
        System.out.println("\t\t\t\t\t|          Select Your Payment Method         |");
        System.out.println("\t\t\t\t\t==============================================");
        System.out.println("\t\t\t\t\t|                                             |");
        System.out.println("\t\t\t\t\t|           [1] Touch 'n Go                   |");
        System.out.println("\t\t\t\t\t|           [2] Credit/Debit Card             |");
        System.out.println("\t\t\t\t\t|                                             |");
        System.out.println("\t\t\t\t\t==============================================");
        System.out.print("\t\t\t\t\tEnter your choice (1-2): ");

        while (true) {
            if (scanner.hasNextInt()) {
                paymentChoice = scanner.nextInt();
                if (paymentChoice == 1 || paymentChoice == 2) {
                    break;
                } else {
                    System.out.print("\t\t\t\t\tInvalid choice. Please enter 1 or 2: ");
                }
            } else {
                System.out.print("\t\t\t\t\tInvalid input. Please enter a number (1 or 2): ");
                scanner.next(); // Clear invalid input
            }
        }

        switch (paymentChoice) {
            case 1:
                handleTouchNGo();
                return "Touch 'n Go";
            case 2:
                handleCreditDebitCard();
                return "Credit/Debit Card";
            default:
                return "Unknown";
        }
    }

    private void handleTouchNGo() {
        Main.clearScreen();
        System.out.println("\t\t\t\t\tYou have selected Touch 'n Go.");
        System.out.print("\t\t\t\t\tEnter your Touch 'n Go account number: ");
        scanner.nextLine(); // Clear any leftover newline

        while (true) {
            String accountNumber = scanner.nextLine().trim();
            if (accountNumber.matches("\\d{10,11}")) {
                System.out.println("\t\t\t\t\tPayment successful with Touch 'n Go.");
                break;
            } else {
                System.out.print("\t\t\t\t\tInvalid account number. Please enter a 10-11 digit number: ");
            }
        }
    }

    private void handleCreditDebitCard() {
        Main.clearScreen();
        System.out.println("\t\t\t\t\tYou have selected Credit/Debit Card.");
        System.out.print("\t\t\t\t\tEnter your card number (16 digits): ");
        scanner.nextLine(); // Clear any leftover newline

        while (true) {
            String cardNumber = scanner.nextLine().trim();
            if (cardNumber.matches("\\d{16}")) {
                System.out.print("\t\t\t\t\tEnter expiration date (MM/YY): ");
                String expiryDate = scanner.nextLine().trim();
                if (expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
                    System.out.print("\t\t\t\t\tEnter CVV (3 digits): ");
                    String cvv = scanner.nextLine().trim();
                    if (cvv.matches("\\d{3}")) {
                        System.out.println("\t\t\t\t\tPayment successful with Credit/Debit Card.");
                        break;
                    } else {
                        System.out.print("\t\t\t\t\tInvalid CVV. Please enter a 3-digit number: ");
                    }
                } else {
                    System.out.print("\t\t\t\t\tInvalid expiration date. Please use MM/YY format: ");
                }
            } else {
                System.out.print("\t\t\t\t\tInvalid card number. Please enter a 16-digit number: ");
            }
        }
    }
}