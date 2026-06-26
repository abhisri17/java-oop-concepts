# Interfaces in Java (Contracts, Implementations, Variables)

## 1. What is an Interface?

An **interface** in Java is a pure contract that defines *what* methods a class must implement, without specifying *how* they are implemented. [geeksforgeeks](https://www.geeksforgeeks.org/java/interfaces-in-java/)

Key points:

- Declared with the `interface` keyword.  
- Cannot be instantiated directly (`new InterfaceName()` is not allowed).  
- A class uses `implements` to fulfill the interface contract.  
- Before Java 8, methods in interfaces were implicitly `public` and `abstract`. Modern Java also allows default, static, and private methods. [w3schools](https://www.w3schools.com/java/java_interface.asp)

Example contract:

```java
interface PaymentProcessor {
    void pay(double amount);   // abstract method (contract)
}
```

This says: any `PaymentProcessor` **must** provide a `pay(double)` method.

***

## 2. Implementing an Interface

A class that **implements** an interface must provide concrete implementations of all its abstract methods, or be declared `abstract` itself. [docs.oracle](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)

Example:

```java
class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via card");
    }
}
```

Usage in a `main` class:

```java
public class InterfaceExample {
    public static void main(String[] args) {
        // Interface reference, concrete object
        PaymentProcessor processor = new CardPaymentProcessor();

        processor.pay(1000.0);  // calls CardPaymentProcessor.pay()
    }
}
```

This demonstrates:

- **Abstraction**: `main` depends on `PaymentProcessor`, not on the concrete class.  
- **Polymorphism**: we could later swap in another implementation (e.g., `UpiPaymentProcessor`) without changing the calling code. [baeldung](https://www.baeldung.com/java-interfaces)

***

## 3. Interface Variables

Interfaces can contain variables, but they behave differently from normal class fields. Any variable declared in an interface is, by default: [javaspring](https://www.javaspring.net/blog/java-interface-variables/)

- `public`  
- `static`  
- `final`  

That is, interface variables are **constants**.

Example:

```java
interface Bank {
    // Interface variable (constant)
    double INTEREST_RATE = 6.5; // actually public static final double INTEREST_RATE = 6.5;

    void displayInterestRate();
}
```

Implications:

- `public` → accessible from anywhere.  
- `static` → belongs to the interface itself; you use `Bank.INTEREST_RATE`.  
- `final` → cannot be changed after initialization.

You must initialize interface variables at declaration:

```java
interface Config {
    int TIMEOUT = 30;  // OK
    // int RETRIES;    // ❌ compile error: must be initialized
}
```

And you cannot assign to them later:

```java
Bank.INTEREST_RATE = 7.0; // ❌ compile error: cannot assign to final variable
```

***

## 4. Example: Interface, Class, Main Class, and Interface Variable

### 4.1 Define interface with method and variable

```java
interface Bank {
    double INTEREST_RATE = 6.5;  // public static final by default

    void displayInterestRate();
}
```

### 4.2 Implement interface in a concrete class

```java
class SbiBank implements Bank {

    @Override
    public void displayInterestRate() {
        // Use interface constant directly inside implementing class
        System.out.println("SBI interest rate: " + INTEREST_RATE);
    }
}
```

### 4.3 Use both in `main` class

```java
public class InterfaceVariableDemo {
    public static void main(String[] args) {
        // Accessing interface variable via interface name (static)
        System.out.println("Bank interest rate (from interface): " + Bank.INTEREST_RATE);

        // Using the implementing class and its method
        SbiBank sbi = new SbiBank();
        sbi.displayInterestRate();
    }
}
```

What this shows clearly: [btechsmartclass](http://www.btechsmartclass.com/java/java-variables-in-interfaces.html)

- Interface variables are used as **constants** via `InterfaceName.VARIABLE`.  
- The implementing class can use these constants and implement the abstract methods.  
- `main` works with both the interface constant and the concrete implementation.

***

## 5. Interface vs Abstract Class (Quick Mental Model)

While both are tools for abstraction: [geeksforgeeks](https://www.geeksforgeeks.org/java/differences-between-interface-and-class-in-java/)

- **Abstract class**:
  - Can have fields, constructors, abstract methods, and concrete methods.  
  - Can be partially implemented.  
  - Single inheritance: a class can extend only one abstract/normal class.

- **Interface**:
  - Primarily defines method signatures and constants.  
  - No constructors, no instantiation.  
  - A class can implement **multiple** interfaces (multiple inheritance of contract).

In backend/Spring code, interfaces are typically used for:

- Service contracts (`UserService`, `PaymentService`, `NotificationService`).  
- Repository contracts (`UserRepository`, etc.).  
- Pluggable behaviors (`PaymentProcessor`, `Logger`, `Auditor`).

***
