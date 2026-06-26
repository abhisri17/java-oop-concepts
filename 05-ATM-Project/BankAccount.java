/**
 * ABSTRACTION: BankAccount is an abstract class.
 * It defines the COMMON blueprint for all bank accounts (Checking, Savings).
 * It cannot be instantiated directly.
 *
 * ENCAPSULATION: All fields are private.
 * They are accessed/modified only through public getter and setter methods.
 */
public abstract class BankAccount {

    // ENCAPSULATION: private fields — cannot be accessed directly from outside
    private String accountNumber;
    private double balance;
    private String accountHolderName;

    // Constructor: initializes common fields for all account types
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        setBalance(initialBalance); // use setter for validation
    }

    // ── Getters (controlled read access) ──────────────────────────────────────

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // ── Setters (controlled write access with validation) ─────────────────────

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("  [ERROR] Balance cannot be negative.");
        }
    }

    // ── Concrete Methods (shared behavior for all account types) ──────────────

    /**
     * Deposit money into the account.
     * Shared by both CheckingAccount and SavingsAccount.
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("  ✔ Deposited ₹" + amount + " successfully.");
        } else {
            System.out.println("  [ERROR] Deposit amount must be greater than zero.");
        }
    }

    /**
     * Withdraw money from the account.
     * Shared base logic; subclasses may override for extra rules.
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.println("  [ERROR] Insufficient balance.");
            return false;
        }
        balance -= amount;
        System.out.println("  ✔ Withdrawn ₹" + amount + " successfully.");
        return true;
    }

    /**
     * Show account details and balance.
     */
    public void checkBalance() {
        System.out.println("  ─────────────────────────────");
        System.out.println("  Account Type   : " + getAccountType());
        System.out.println("  Account Number : " + accountNumber);
        System.out.println("  Holder Name    : " + accountHolderName);
        System.out.println("  Balance        : ₹" + balance);
        System.out.println("  ─────────────────────────────");
    }

    // ── Abstract Method (ABSTRACTION) ─────────────────────────────────────────

    /**
     * ABSTRACTION: Abstract method — every subclass MUST tell what type it is.
     * BankAccount does not know HOW to implement this; only subclasses know.
     *
     * POLYMORPHISM: This method will behave differently for each subclass
     * (CheckingAccount returns "Checking", SavingsAccount returns "Savings").
     */
    public abstract String getAccountType();
}