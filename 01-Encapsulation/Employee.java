/**
 * Encapsulation example:
 * Employee details with private fields and controlled access.
 */
public class Employee {

    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        setSalary(salary);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    // Setter with simple validation
    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            System.out.println("Salary cannot be negative.");
        }
    }

    public void giveRaise(double percentage) {
        if (percentage > 0) {
            double increment = salary * (percentage / 100);
            salary += increment;
            System.out.println("Salary increased by " + percentage + "%");
        } else {
            System.out.println("Percentage must be > 0.");
        }
    }

    public static void main(String[] args) {
        Employee emp = new Employee(101, "Priya", 30000);
        emp.giveRaise(10);
        System.out.println("New salary: " + emp.getSalary());
    }
}