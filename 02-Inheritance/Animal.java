/**
 * Base class for inheritance example.
 */
public class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println("Animal makes a sound.");
    }
}