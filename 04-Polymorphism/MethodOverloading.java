/**
 * Compile-time polymorphism:
 * Same method name, different parameter list.
 */
public class MethodOverloading {

    // Overloaded methods
    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        MethodOverloading m = new MethodOverloading();
        System.out.println(m.add(2, 3));
        System.out.println(m.add(2, 3, 4));
        System.out.println(m.add(2.5, 3.5));
    }
}