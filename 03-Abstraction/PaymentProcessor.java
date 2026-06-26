/**
 * Abstraction using interface.
 * Defines WHAT a payment processor must do.
 */
public interface PaymentProcessor {

    void pay(double amount);
}