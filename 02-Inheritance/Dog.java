/**
 * Dog IS-A Animal (inherits Animal).
 */
public class Dog extends Animal {

    public Dog(String name) {
        super(name); // call Animal constructor
    }

    @Override
    public void speak() {
        System.out.println(name + " barks.");
    }

    public void fetch() {
        System.out.println(name + " is fetching the ball.");
    }

    public static void main(String[] args) {
        Dog d = new Dog("Bruno");
        d.speak();
        d.fetch();
    }
}