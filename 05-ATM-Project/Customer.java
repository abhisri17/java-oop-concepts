/**
 * Represents an ATM customer.
 * ENCAPSULATION: All fields are private; accessed via getters.
 *
 * Each customer can have:
 * - A Checking Account
 * - A Savings Account
 *
 * POLYMORPHISM: Both accounts are stored as BankAccount references (abstract type),
 * but hold concrete CheckingAccount / SavingsAccount objects.
 */
public class Customer {

    // ENCAPSULATION: private fields
    private String customerId;
    private String pin;
    private String name;

    // POLYMORPHISM: abstract reference (BankAccount), concrete objects (Checking/Savings)
    private BankAccount checkingAccount;
    private BankAccount savingsAccount;

    public Customer(String customerId, String pin, String name,
                    BankAccount checkingAccount, BankAccount savingsAccount) {
        this.customerId = customerId;
        this.pin = pin;
        this.name = name;
        this.checkingAccount = checkingAccount;
        this.savingsAccount = savingsAccount;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public BankAccount getCheckingAccount() { return checkingAccount; }
    public BankAccount getSavingsAccount() { return savingsAccount; }

    /**
     * Validates the customer's PIN.
     */
    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }
}