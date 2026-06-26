/**
 * Car IS-A Vehicle (inherits Vehicle).
 */
public class Car extends Vehicle {

    private int numberOfDoors;

    public Car(String brand, int year, int numberOfDoors) {
        super(brand, year); // call Vehicle constructor
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void start() {
        System.out.println("Car " + brand + " is starting with key.");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Doors: " + numberOfDoors);
    }

    public static void main(String[] args) {
        Car c = new Car("Toyota", 2024, 4);
        c.start();
        c.displayInfo();
    }
}