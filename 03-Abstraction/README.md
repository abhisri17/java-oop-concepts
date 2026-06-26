# Abstraction in Java: Abstract Classes & Interfaces

## 1. Core Idea of Abstraction

Abstraction in Java means:

- **Hiding implementation details** and exposing only the **essential operations/contract**.
- Code uses a clean API (methods) without needing to know *how* the class does its work internally.

You implement abstraction mainly with:

- **Abstract classes**
- **Abstract methods**
- **Interfaces**

***

## 2. Abstract Classes

### 2.1 What is an abstract class?

An abstract class:

- Is declared with the `abstract` keyword.
- **Cannot be instantiated directly** (no `new AbstractClass()`).
- Acts as a **blueprint/base** for subclasses.
- Can contain abstract methods (no body), concrete methods, fields, and constructors.

Example:

```java
abstract class Shape {
    void display() {
        System.out.println("This is a shape");
    }

    abstract double area();
}
```

You can't do:

```java
Shape s = new Shape(); // compile error: Shape is abstract
```

You must extend it:

```java
class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}
```

***

## 3. Abstract Methods

### 3.1 What is an abstract method?

An abstract method:

- Is declared with `abstract` and **no body** (only signature and `;`).
- Represents a **"must implement"** contract for subclasses.
- Can only appear inside an abstract class or interface.

```java
abstract class Shape {
    abstract double area();  // abstract method, no body
}
```

> Any **non-abstract subclass** must implement all inherited abstract methods; otherwise, it must itself be declared abstract.

### 3.2 "Otherwise it must itself be abstract" explained

If `Rectangle` extends `Shape` but doesn't implement `area()`, you must either implement it:

```java
class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
```

or mark `Rectangle` itself as abstract:

```java
abstract class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    // still missing area(), so Rectangle must be abstract
}
```

***

## 4. Abstract Reference, Concrete Object

### 4.1 What does this pattern mean?

```java
Shape s = new Circle(3);
```

- **Reference type**: `Shape` (abstract class)
- **Actual object**: `new Circle(3)` (concrete subclass)

Full example:

```java
abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return Math.PI * radius * radius; }
}

class Rectangle extends Shape {
    double length, width;
    Rectangle(double length, double width) { this.length = length; this.width = width; }

    @Override
    double area() { return length * width; }
}

public class AbstractReferenceDemo {
    public static void main(String[] args) {
        Shape s1 = new Circle(3);
        Shape s2 = new Rectangle(4, 5);

        System.out.println(s1.area());  // uses Circle.area()
        System.out.println(s2.area());  // uses Rectangle.area()
    }
}
```

Benefits:

- Code depends on `Shape` (abstraction), not specific classes.
- You can swap implementations without changing calling code.
- Polymorphism: same reference type, different behavior at runtime.

***

## 5. Constructors in Abstract Classes

### 5.1 Can an abstract class have a constructor?

Yes. Even though you can't instantiate an abstract class directly, its constructor:

- Initializes **common fields**.
- Runs as part of the construction chain via `super(...)` when a subclass is instantiated.

### 5.2 Example

```java
abstract class Shape {
    String name;

    Shape(String name) {
        this.name = name;
        System.out.println("Shape constructor: " + name);
    }

    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(String name, double radius) {
        super(name);  // calls abstract class constructor
        this.radius = radius;
        System.out.println("Circle constructor: radius = " + radius);
    }

    @Override
    double area() { return Math.PI * radius * radius; }
}

public class AbstractConstructorDemo {
    public static void main(String[] args) {
        Shape s = new Circle("MyCircle", 3);
        System.out.println("Area: " + s.area());
    }
}
```

Execution order for `new Circle("MyCircle", 3)`:

1. `Circle` constructor starts.
2. `super(name)` calls `Shape(String name)` → initializes `name`, prints `Shape constructor: MyCircle`.
3. `Circle` constructor finishes → initializes `radius`, prints `Circle constructor: radius = 3`.
4. `Shape` reference `s` now points to a fully constructed `Circle`.

***

## 6. Default Constructor Behaviour (`super()` and Abstract Classes)

### Case 1: Abstract class has a no-arg constructor → auto call works

```java
abstract class Animal {
    Animal() { System.out.println("Animal default constructor"); }
}

class Dog extends Animal {
    Dog() { System.out.println("Dog constructor"); }
    // compiler auto-inserts super(); here
}
```

Output:
```
Animal default constructor
Dog constructor
```

### Case 2: Abstract class has only a parameterized constructor → must call explicitly

```java
abstract class Animal {
    Animal(String name) { System.out.println("Animal constructor: " + name); }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);  // required — no no-arg Animal() exists
        System.out.println("Dog constructor: " + name);
    }
}
```

If you omit `super(name)`, the compiler tries `super()` which doesn't exist → **compile error**.

> The abstract superclass's no-arg constructor is auto-called if present. If only parameterized constructors exist, you **must** explicitly call one with `super(args)`.

***

## 7. Interfaces

### 7.1 What is an Interface?

An **interface** is a pure contract that defines *what* methods a class must implement, without specifying *how*.

Key points:

- Declared with the `interface` keyword.
- Cannot be instantiated directly.
- A class uses `implements` to fulfill the contract.
- Before Java 8, all methods were implicitly `public abstract`; modern Java also allows `default`, `static`, and `private` methods.

```java
interface PaymentProcessor {
    void pay(double amount);  // abstract method (contract)
}
```

### 7.2 Implementing an Interface

A class implementing an interface must provide concrete implementations of all abstract methods, or itself be declared `abstract`.

```java
class CardPaymentProcessor implements PaymentProcessor {
    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via card");
    }
}

public class InterfaceExample {
    public static void main(String[] args) {
        PaymentProcessor processor = new CardPaymentProcessor();
        processor.pay(1000.0);
    }
}
```

This demonstrates abstraction + polymorphism: `main` depends on `PaymentProcessor`, not the concrete class. Swapping in `UpiPaymentProcessor` requires zero changes to calling code.

***

## 8. Interface Variables

Any variable declared in an interface is implicitly `public static final` — i.e., a **constant**.

```java
interface Bank {
    double INTEREST_RATE = 6.5;  // public static final by default
    void displayInterestRate();
}
```

Implications:

- `public` → accessible from anywhere.
- `static` → belongs to the interface; access via `Bank.INTEREST_RATE`.
- `final` → cannot be reassigned.

```java
interface Config {
    int TIMEOUT = 30;   // OK
    // int RETRIES;     // ❌ must be initialized
}

Bank.INTEREST_RATE = 7.0; // ❌ cannot assign to final variable
```

### 8.1 Full Example

```java
interface Bank {
    double INTEREST_RATE = 6.5;
    void displayInterestRate();
}

class SbiBank implements Bank {
    @Override
    public void displayInterestRate() {
        System.out.println("SBI interest rate: " + INTEREST_RATE);
    }
}

public class InterfaceVariableDemo {
    public static void main(String[] args) {
        System.out.println("Rate (from interface): " + Bank.INTEREST_RATE);

        SbiBank sbi = new SbiBank();
        sbi.displayInterestRate();
    }
}
```

***

## 9. Abstract Class vs Interface — Quick Mental Model

| | Abstract Class | Interface |
|---|---|---|
| Fields | Instance fields allowed | Constants only (`public static final`) |
| Constructors | Yes | No |
| Methods | Abstract + concrete | Abstract + default/static (Java 8+) |
| Inheritance | Single (`extends`) | Multiple (`implements`) |
| Instantiation | No | No |

**Abstract class** = partial implementation with shared state (e.g., `Shape` with a `name` field).

**Interface** = pure contract for pluggable behavior (e.g., `PaymentProcessor`, `UserService`, `Logger` in Spring).

***

## 10. Summary

- **Abstraction**: hide implementation, expose essential behavior.
- **Abstract classes**: blueprints with abstract + concrete methods, cannot be instantiated.
- **Abstract methods**: no body; non-abstract subclasses must implement them or be declared abstract themselves.
- **Abstract reference, concrete object**: `Shape s = new Circle(3)` — enables polymorphism.
- **Constructors in abstract classes**: initialize shared state, always run via `super(...)` from subclasses.
- **`super()` behavior**: implicit no-arg call if available; otherwise explicit `super(args)` required.
- **Interfaces**: pure contracts; variables are `public static final` constants; a class can implement multiple interfaces.