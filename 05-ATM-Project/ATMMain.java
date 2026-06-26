import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * ATM Main Class — entry point of the application.
 *
 * Flow:
 * 1. Pre-defined customers are loaded into a map (like a small database).
 * 2. User logs in using Customer ID + PIN.
 * 3. After login, user chooses Checking or Savings account.
 * 4. In each account, user can: Check Balance, Deposit, Withdraw.
 */
public class ATMMain {

    // Pre-defined customers stored in a Map (CustomerID → Customer)
    // ENCAPSULATION: Customer data is managed via Customer objects
    private static Map<String, Customer> customers = new HashMap<>();

    /**
     * Load pre-defined customers into the system.
     * In a real app, this would come from a database.
     *
     * POLYMORPHISM: checkingAccount and savingsAccount are stored as BankAccount
     * (abstract type) but hold CheckingAccount / SavingsAccount objects.
     */
    private static void loadCustomers() {

        // Customer 1: Abhinav
        BankAccount abhinav_checking = new CheckingAccount("CHK001", "Abhinav", 15000, 5000);
        BankAccount abhinav_savings  = new SavingsAccount("SAV001", "Abhinav", 50000, 1000, 4.5);
        customers.put("C001", new Customer("C001", "1234", "Abhinav", abhinav_checking, abhinav_savings));

        // Customer 2: Priya
        BankAccount priya_checking = new CheckingAccount("CHK002", "Priya", 8000, 2000);
        BankAccount priya_savings  = new SavingsAccount("SAV002", "Priya", 25000, 500, 5.0);
        customers.put("C002", new Customer("C002", "5678", "Priya", priya_checking, priya_savings));

        // Customer 3: Ravi
        BankAccount ravi_checking = new CheckingAccount("CHK003", "Ravi", 12000, 3000);
        BankAccount ravi_savings  = new SavingsAccount("SAV003", "Ravi", 40000, 1000, 4.0);
        customers.put("C003", new Customer("C003", "9012", "Ravi", ravi_checking, ravi_savings));
    }

    /**
     * Login function: verifies Customer ID and PIN.
     * Returns the matched Customer object or null if login fails.
     */
    private static Customer login(Scanner sc) {
        System.out.println("\n  ╔══════════════════════════╗");
        System.out.println("  ║      Welcome to ATM      ║");
        System.out.println("  ╚══════════════════════════╝");
        System.out.print("  Enter Customer ID : ");
        String id = sc.nextLine().trim();

        System.out.print("  Enter PIN          : ");
        String pin = sc.nextLine().trim();

        // Look up customer in map
        Customer customer = customers.get(id);

        if (customer == null) {
            System.out.println("  [ERROR] Customer ID not found.");
            return null;
        }

        if (!customer.validatePin(pin)) {
            System.out.println("  [ERROR] Incorrect PIN.");
            return null;
        }

        System.out.println("\n  ✔ Login successful! Welcome, " + customer.getName() + ".");
        return customer;
    }

    /**
     * Account operations menu: Withdraw, Deposit, Check Balance.
     *
     * POLYMORPHISM: 'account' is a BankAccount reference.
     * When we call account.withdraw() or account.deposit(),
     * the correct overridden version (CheckingAccount or SavingsAccount) runs at RUNTIME.
     * This is Runtime Polymorphism (Method Overriding + Dynamic Dispatch).
     */
    private static void accountMenu(Scanner sc, BankAccount account) {
        boolean back = false;
        while (!back) {
            System.out.println("\n  ── " + account.getAccountType() + " Menu ──");
            System.out.println("  1. Check Balance");
            System.out.println("  2. Deposit");
            System.out.println("  3. Withdraw");
            System.out.println("  4. Back");
            System.out.print("  Choose option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // calls checkBalance() from BankAccount (shared concrete method)
                    account.checkBalance();
                    break;

                case "2":
                    System.out.print("  Enter deposit amount: ₹");
                    try {
                        double amount = Double.parseDouble(sc.nextLine().trim());
                        // calls deposit() from BankAccount (shared concrete method)
                        account.deposit(amount);
                    } catch (NumberFormatException e) {
                        System.out.println("  [ERROR] Invalid amount entered.");
                    }
                    break;

                case "3":
                    System.out.print("  Enter withdrawal amount: ₹");
                    try {
                        double amount = Double.parseDouble(sc.nextLine().trim());
                        // POLYMORPHISM: calls the correct overridden withdraw() at runtime
                        // If account is CheckingAccount → CheckingAccount.withdraw() runs
                        // If account is SavingsAccount  → SavingsAccount.withdraw() runs
                        account.withdraw(amount);
                    } catch (NumberFormatException e) {
                        System.out.println("  [ERROR] Invalid amount entered.");
                    }
                    break;

                case "4":
                    back = true;
                    break;

                default:
                    System.out.println("  [ERROR] Invalid option. Try again.");
            }
        }
    }

    /**
     * Account type selection menu: Checking or Savings.
     */
    private static void selectAccountMenu(Scanner sc, Customer customer) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n  ── Select Account ──");
            System.out.println("  1. Checking Account");
            System.out.println("  2. Savings Account");
            System.out.println("  3. Logout");
            System.out.print("  Choose option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // POLYMORPHISM: abstract reference (BankAccount), concrete object (CheckingAccount)
                    accountMenu(sc, customer.getCheckingAccount());
                    break;

                case "2":
                    // POLYMORPHISM: abstract reference (BankAccount), concrete object (SavingsAccount)
                    accountMenu(sc, customer.getSavingsAccount());
                    break;

                case "3":
                    System.out.println("\n  ✔ Logged out. Thank you, " + customer.getName() + "!");
                    logout = true;
                    break;

                default:
                    System.out.println("  [ERROR] Invalid option. Try again.");
            }
        }
    }

    /**
     * Main entry point of the ATM application.
     */
    public static void main(String[] args) {
        // Load pre-defined customers
        loadCustomers();

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Step 1: Login
            Customer customer = login(sc);

            if (customer != null) {
                // Step 2: Account type selection + operations
                selectAccountMenu(sc, customer);
            }

            // Ask if user wants to try again or exit
            System.out.print("\n  Return to ATM screen? (yes/no): ");
            String again = sc.nextLine().trim().toLowerCase();
            if (!again.equals("yes")) {
                exit = true;
                System.out.println("\n  ╔══════════════════════════════╗");
                System.out.println("  ║  Thank you for using the ATM ║");
                System.out.println("  ╚══════════════════════════════╝");
            }
        }

        sc.close();
    }
}