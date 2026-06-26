/**
 * INHERITANCE: CheckingAccount extends BankAccount.
 * It inherits all fields (balance, accountNumber) and methods (deposit, withdraw, checkBalance).
 *
 * POLYMORPHISM: Overrides getAccountType() and withdraw() with its own logic.
 */
public class CheckingAccount extends BankAccount {

    // Extra field specific to CheckingAccount only
    private double overdraftLimit;

    /**
     * Constructor calls super() to initialize common fields in BankAccount.
     */
    public CheckingAccount(String accountNumber, String accountHolderName,
                           double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialBalance); // calls BankAccount constructor
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * POLYMORPHISM (Method Overriding): Returns account type specific to this class.
     */
    @Override
    public String getAccountType() {
        return "Checking Account";
    }

    /**
     * POLYMORPHISM (Method Overriding): CheckingAccount allows overdraft up to a limit.
     * This overrides the base withdraw() in BankAccount with extra checking-specific logic.
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Withdrawal amount must be greater than zero.");
            return false;
        }
        // Checking accounts can go into overdraft up to the overdraftLimit
        if (amount > getBalance() + overdraftLimit) {
            System.out.println("  [ERROR] Exceeds overdraft limit of ₹" + overdraftLimit);
            return false;
        }
        setBalance(getBalance() - amount);
        System.out.println("  ✔ Withdrawn ₹" + amount + " successfully.");
        if (getBalance() < 0) {
            System.out.println("  [WARNING] Your account is in overdraft! Balance: ₹" + getBalance());
        }
        return true;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}