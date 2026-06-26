/**
 * Concrete implementation of PaymentProcessor.
 */
public class CardPayment implements PaymentProcessor {

    @Override
    public void pay(double amount) {
        System.out.println("Paying ₹" + amount + " by card.");
    }

    public static void main(String[] args) {
        PaymentProcessor processor = new CardPayment();
        processor.pay(1500);
    }
}