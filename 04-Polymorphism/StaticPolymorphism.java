/**
 * Static methods can be overloaded (compile-time polymorphism).
 */
public class StaticPolymorphism {

    public static void printSum() {
        System.out.println("No numbers to add.");
    }

    public static void printSum(int a, int b) {
        System.out.println("Sum of ints: " + (a + b));
    }

    public static void printSum(double a, double b) {
        System.out.println("Sum of doubles: " + (a + b));
    }

    public static void main(String[] args) {
        StaticPolymorphism.printSum();
        StaticPolymorphism.printSum(2, 3);
        StaticPolymorphism.printSum(2.5, 3.5);
    }
}