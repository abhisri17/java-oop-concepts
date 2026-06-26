/**
 * Cat IS-A Animal (inherits Animal).
 */
public class Cat extends Animal {

    public Cat(String name) {
        super(name);
    }

    @Override
    public void speak() {
        System.out.println(name + " meows.");
    }

    public static void main(String[] args) {
        Cat c = new Cat("Milo");
        c.speak();
    }
}