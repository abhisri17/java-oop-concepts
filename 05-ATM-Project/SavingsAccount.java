/**
 * INHERITANCE: SavingsAccount extends BankAccount.
 * It inherits all fields and methods from BankAccount.
 *
 * POLYMORPHISM: Overrides getAccountType() with its own specific return value.
 * Also adds savings-specific behavior (minimum balance rule on withdraw).
 */
public class SavingsAccount extends BankAccount {

    // Extra field specific to SavingsAccount only
    private double minimumBalance;
    private double interestRate; // annual interest rate in %

    /**
     * Constructor calls super() to initialize common BankAccount fields.
     */
    public SavingsAccount(String accountNumber, String accountHolderName,
                          double initialBalance, double minimumBalance, double interestRate) {
        super(accountNumber, accountHolderName, initialBalance); // calls BankAccount constructor
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    /**
     * POLYMORPHISM (Method Overriding): Returns account type specific to this class.
     */
    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    /**
     * POLYMORPHISM (Method Overriding): SavingsAccount enforces a minimum balance rule.
     * This overrides the base withdraw() in BankAccount with savings-specific logic.
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Withdrawal amount must be greater than zero.");
            return false;
        }
        // After withdrawal, balance must not drop below minimum balance
        if ((getBalance() - amount) < minimumBalance) {
            System.out.println("  [ERROR] Cannot withdraw. Minimum balance of ₹"
                + minimumBalance + " must be maintained.");
            return false;
        }
        setBalance(getBalance() - amount);
        System.out.println("  ✔ Withdrawn ₹" + amount + " successfully.");
        return true;
    }

    /**
     * Apply annual interest to the savings account.
     * A savings-specific method (not in BankAccount).
     */
    public void applyInterest() {
        double interest = getBalance() * (interestRate / 100);
        setBalance(getBalance() + interest);
        System.out.println("  ✔ Interest of ₹" + interest + " applied at " + interestRate + "% rate.");
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
}