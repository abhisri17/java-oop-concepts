# Abstraction in Java (Abstract Classes, Methods, Constructors, Usage)

## 1. Core Idea of Abstraction

Abstraction in Java means:

- **Hiding implementation details** and exposing only the **essential operations/contract**.  
- Code uses a clean API (methods) without needing to know *how* the class does its work internally. [geeksforgeeks](https://www.geeksforgeeks.org/java/abstraction-in-java-2/)

You implement abstraction mainly with:

- **Abstract classes**  
- **Abstract methods**  
- **Interfaces** (later)

***

## 2. Abstract Classes

### 2.1 What is an abstract class?

An abstract class: [w3schools](https://www.w3schools.com/java/java_abstract.asp)

- Is declared with the `abstract` keyword.  
- **Cannot be instantiated directly** (no `new AbstractClass()`).  
- Acts as a **blueprint/base** for subclasses.  
- Can contain:
  - abstract methods (no body)  
  - concrete methods (normal methods)  
  - fields  
  - constructors

Example:

```java
abstract class Shape {
    // concrete method: has implementation
    void display() {
        System.out.println("This is a shape");
    }

    // abstract method: no implementation here
    abstract double area();
}
```

You can’t do:

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

An abstract method: [docs.oracle](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)

- Is declared with `abstract` and **no body** (only signature and `;`).  
- Represents a **“must implement”** contract for subclasses.  
- Can only appear inside an abstract class (or interface).

Example:

```java
abstract class Shape {
    abstract double area();  // abstract method, no body
}
```

Rule:

> Any **non-abstract subclass** must implement all inherited abstract methods; otherwise, it must itself be declared abstract. [cs.cornell](https://www.cs.cornell.edu/courses/JavaAndDS/abstractInterface/01abstractClass.pdf)

### 3.2 “Otherwise it must itself be abstract” explained

Consider:

```java
abstract class Shape {
    abstract double area();
}

// ❌ Incorrect concrete subclass
class Rectangle extends Shape {
    double length;
    double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Missing implementation of area()
}
```

- `Rectangle` extends `Shape`, which has abstract `area()`.  
- `Rectangle` does **not** implement `area()`.  
- It’s still “incomplete”, so Java requires:

Either implement the abstract method:

```java
class Rectangle extends Shape {
    double length;
    double width;

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

or mark `Rectangle` as abstract:

```java
abstract class Rectangle extends Shape {
    double length;
    double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // still missing area(), so Rectangle must be abstract too
}
```

That’s what the line means:

> If a class includes (or inherits) abstract methods and does **not** implement them, **it must itself be declared abstract**. [whitman](https://www.whitman.edu/mathematics/java_tutorial/java/javaOO/abstract.html)

***

## 4. Abstract Reference, Concrete Object

### 4.1 What does “abstract reference, concrete object” mean?

Pattern:

```java
Shape s = new Circle(3);
```

- **Reference type**: `Shape` (abstract class)  
- **Actual object created**: `new Circle(3)` (concrete subclass)

You can’t create a `Shape` directly, but you can **hold** a `Circle` in a `Shape` reference. This is key for polymorphism and abstraction: [datacamp](https://www.datacamp.com/doc/java/abstraction)

```java
abstract class Shape {
    abstract double area();
}

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

class Rectangle extends Shape {
    double length;
    double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}

public class AbstractReferenceDemo {
    public static void main(String[] args) {
        Shape s1 = new Circle(3);       // abstract reference, concrete object
        Shape s2 = new Rectangle(4, 5); // abstract reference, concrete object

        System.out.println(s1.area());  // uses Circle.area()
        System.out.println(s2.area());  // uses Rectangle.area()
    }
}
```

Benefits:

- Code uses `Shape` (abstraction), not specific classes.  
- You can swap implementations (Circle, Rectangle, Triangle) without changing calling code.  
- Polymorphism: same `Shape` reference, different `area()` behavior at runtime. [docs.oracle](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)

***

## 5. Constructors in Abstract Classes

### 5.1 Can an abstract class have a constructor?

Yes. Abstract classes **can** have constructors. [geeksforgeeks](https://www.geeksforgeeks.org/java/constructor-in-java-abstract-class/)

Even though you can’t instantiate an abstract class directly, its constructor:

- Initializes **common fields**.  
- Runs as part of the construction chain of any concrete subclass (via `super(...)`). [baeldung](https://www.baeldung.com/java-abstract-classes-constructors)

### 5.2 Example: constructor in abstract class + abstract reference, concrete object

```java
abstract class Shape {
    String name;

    Shape(String name) {                 // constructor in abstract class
        this.name = name;
        System.out.println("Shape constructor: " + name);
    }

    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(String name, double radius) {
        super(name);                     // call abstract class constructor
        this.radius = radius;
        System.out.println("Circle constructor: radius = " + radius);
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

public class AbstractConstructorDemo {
    public static void main(String[] args) {
        Shape s = new Circle("MyCircle", 3);  // abstract reference, concrete object

        System.out.println("Area: " + s.area());
    }
}
```

Execution order when `new Circle("MyCircle", 3)` runs:

1. `Circle` constructor starts.  
2. First line `super(name)` calls `Shape(String name)`:
   - Initializes `name` field.
   - Prints `Shape constructor: MyCircle`.  
3. Then `Circle` constructor finishes:
   - Initializes `radius`.
   - Prints `Circle constructor: radius = 3`.  
4. The `Shape` reference `s` now points to a fully constructed `Circle` object.

So:

- **Abstract reference**: `Shape s`  
- **Concrete object**: `new Circle(...)`  
- Abstract class constructor executes as part of object creation.

***

## 6. Default Constructor Behaviour (`super()` and abstract classes)

### 6.1 What if you don’t explicitly call `super()`?

Java rule:

- If you don’t write `super(...)` or `this(...)` in a constructor, the compiler **automatically inserts** `super()` as the first statement.  
- `super()` calls the **no-argument constructor** of the immediate superclass. [stackoverflow](https://stackoverflow.com/questions/23098139/does-java-call-an-abstract-classes-constructor-automatically)

This applies even if the superclass is abstract.

#### Case 1: Abstract class has a no-arg constructor → auto call works

```java
abstract class Animal {
    Animal() {                      // default constructor
        System.out.println("Animal default constructor");
    }
}

class Dog extends Animal {
    Dog() {                         // no explicit super()
        System.out.println("Dog constructor");
    }
}

public class Demo1 {
    public static void main(String[] args) {
        Dog d = new Dog();
    }
}
```

Execution:

- Compiler inserts `super();` into `Dog()` → calls `Animal()` automatically.  
- Output:
  - `Animal default constructor`
  - `Dog constructor`

So:

> If I do not call `super`, the default constructor of the abstract class will be called

is **true** in this case (because a no-arg constructor exists).

#### Case 2: Abstract class has only parameterized constructor → must call `super(args)`

```java
abstract class Animal {
    Animal(String name) {           // only parameterized constructor
        System.out.println("Animal constructor: " + name);
    }
}

class Dog extends Animal {
    Dog(String name) {
        // implicit super(); would be inserted, but there is NO no-arg Animal()
        super(name);                // must call explicitly
        System.out.println("Dog constructor: " + name);
    }
}

public class Demo2 {
    public static void main(String[] args) {
        Dog d = new Dog("Buddy");
    }
}
```

If you omit `super(name)`:

- Compiler tries `super();` which doesn’t exist → **compile error**. [stackoverflow](https://stackoverflow.com/questions/43096004/super-in-abstract-class)
- You must explicitly call a valid constructor of the abstract superclass.

So the refined rule:

> The abstract superclass’s **no-arg** constructor is auto-called if present and you skip `super()`.  
> If the abstract superclass only has parameterized constructors, you **must** explicitly call one of them with `super(args)` from the subclass.

***

## 7. Abstraction Summary (Abstract Classes + Constructors + References)

- **Abstraction**: hide implementation, expose essential behavior.  
- **Abstract classes**: cannot be instantiated, used as blueprints with abstract + concrete methods. [geeksforgeeks](https://www.geeksforgeeks.org/java/abstraction-in-java-2/)
- **Abstract methods**: methods without bodies that subclasses must implement; if not, subclass must be abstract. [idratherbewriting](https://idratherbewriting.com/java-abstract-methods/)
- **Abstract reference, concrete object**: reference of abstract type holding an instance of a concrete subclass: `Shape s = new Circle(3);`. [cs.cornell](https://www.cs.cornell.edu/courses/JavaAndDS/abstractInterface/01abstractClass.pdf)
- **Constructors in abstract classes**: initialize shared state and always run via `super(...)` when a subclass is instantiated. [sebhastian](https://sebhastian.com/java-abstract-class-constructor/)
- **`super()` behavior**:
  - implicit `super()` calls abstract superclass’s no-arg constructor if it exists;  
  - otherwise, you must explicitly call a matching constructor.

***
