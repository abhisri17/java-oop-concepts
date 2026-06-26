/**
 * Runtime polymorphism via method overriding.
 */
class AnimalPoly {
    public void speak() {
        System.out.println("Animal speaks");
    }
}

class DogPoly extends AnimalPoly {
    @Override
    public void speak() {
        System.out.println("Dog barks");
    }
}

public class MethodOverriding {

    public static void main(String[] args) {
        AnimalPoly a = new DogPoly(); // upcasting
        a.speak(); // calls DogPoly.speak() at runtime
    }
}