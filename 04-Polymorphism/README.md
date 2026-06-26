# Polymorphism in Java and Static Keyword (Interview-Focused Notes)

## 1. Polymorphism – Core Idea

Polymorphism = “many forms”. In Java: [scientecheasy](https://www.scientecheasy.com/2021/02/polymorphism-interview-questions.html/)

> The same method name call can result in **different behavior** depending on the actual object it is acting on.

Two major forms in Java:

- **Compile-time polymorphism** → achieved via **method overloading**. [medium](https://medium.com/@nitish.d599/runtime-vs-compile-time-polymorphism-in-java-explained-with-examples-04ce2489c2ba)
- **Runtime polymorphism** → achieved via **method overriding**. [enjoyalgorithms](https://www.enjoyalgorithms.com/blog/difference-between-compile-time-and-runtime-polymorphism/)

Interviewers love these distinctions.

***

## 2. Compile-Time Polymorphism (Method Overloading)

### 2.1 Definition

Method overloading:

- Same method name, **different parameter list** (number, type, or order)  
- In the **same class**. [medium](https://medium.com/@rwijayabandu/method-overloading-vs-method-overriding-in-java-661dd6889748)

Example:

```java
class Calculator {

    int add(int a, int b) {                  // version 1
        return a + b;
    }

    double add(double a, double b) {        // version 2: different parameter types
        return a + b;
    }

    int add(int a, int b, int c) {          // version 3: different parameter count
        return a + b + c;
    }
}
```

Usage:

```java
Calculator calc = new Calculator();
calc.add(2, 3);         // calls int add(int, int)
calc.add(2.5, 3.5);     // calls double add(double, double)
calc.add(1, 2, 3);      // calls int add(int, int, int)
```

The compiler decides which `add` to call based on arguments → **compile-time/static/early binding**. [refreshjava](https://refreshjava.com/java/runtime-and-compile-time-polymorphism)

Important points: [geeksforgeeks](https://www.geeksforgeeks.org/java/difference-between-method-overloading-and-method-overriding-in-java/)

- Overloading is **compile-time polymorphism**.  
- Return type alone **cannot** be used to overload (parameters must change).  
- Static methods **can be overloaded** (see section 5).

***

## 3. Runtime Polymorphism (Method Overriding)

### 3.1 Definition

Method overriding:

- A child class provides a **new implementation** for a method with the **same signature** as in the parent class. [stackoverflow](https://stackoverflow.com/questions/2469767/java-overloading-and-overriding)

Example:

```java
class Animal {
    void speak() {
        System.out.println("Animal speaks");
    }
}

class Dog extends Animal {
    @Override
    void speak() {
        System.out.println("Dog barks");
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        Animal a = new Dog();   // base/reference type, child object
        a.speak();              // prints "Dog barks"
    }
}
```

Explanation:

- Reference type is `Animal`.  
- Actual object is `Dog`.  
- At **runtime**, JVM checks the real object type (`Dog`) and calls `Dog.speak()`. [geeksforgeeks](https://www.geeksforgeeks.org/java/difference-between-compile-time-and-run-time-polymorphism-in-java/)
- This is **runtime/dynamic/late binding** polymorphism.

Rules for overriding: [medium](https://medium.com/@rwijayabandu/method-overloading-vs-method-overriding-in-java-661dd6889748)

- Same method signature (name + parameters).  
- Same or **covariant** return type (subtype of parent’s return).  
- Access modifier cannot be more restrictive.  
- Only instance methods participate; static methods do **not** truly override.

***

## 4. Overloading vs Overriding (Classic Interview Comparison)

Key differences: [stackoverflow](https://stackoverflow.com/questions/154577/polymorphism-vs-overriding-vs-overloading)

- **Overloading**:
  - Same class.  
  - Same method name, different parameters.  
  - Achieves **compile-time polymorphism**.  
  - Does not require inheritance.  
  - Method chosen at compile time based on argument types (signature).

- **Overriding**:
  - Parent–child relationship (inheritance).  
  - Same signature, different implementation.  
  - Achieves **runtime polymorphism**.  
  - Requires inheritance.  
  - Method chosen at runtime based on actual object type.

Typical interview questions: [testbook](https://testbook.com/interview/java-polymorphism-interview-questions)

- “Explain polymorphism with examples.”  
- “Difference between compile-time and runtime polymorphism.”  
- “Difference between method overloading and method overriding.”  
- “Can static methods be overloaded? Can they be overridden?”

***

## 5. Static Methods and Polymorphism

### 5.1 “Static methods can be overloaded” – what it means

Static methods belong to the **class**, not to instance objects. [freecodecamp](https://www.freecodecamp.org/news/static-variables-in-java/)

Overloading works for them just like for instance methods:

```java
class MathUtil {

    // 1. Static method with no parameters
    public static void printSum() {
        System.out.println("No numbers to add");
    }

    // 2. Static method with two int parameters (overloaded)
    public static void printSum(int a, int b) {
        System.out.println("Sum of ints: " + (a + b));
    }

    // 3. Static method with two double parameters (overloaded)
    public static void printSum(double a, double b) {
        System.out.println("Sum of doubles: " + (a + b));
    }

    public static void main(String[] args) {
        MathUtil.printSum();          // calls version 1
        MathUtil.printSum(2, 3);      // calls version 2
        MathUtil.printSum(2.5, 3.5);  // calls version 3
    }
}
```

Here:

- All methods are named `printSum`.  
- They differ in parameter list → **overloading**.  
- The compiler picks the correct one at compile time based on arguments. [blog.stackademic](https://blog.stackademic.com/tricky-core-java-interview-questions-interviewers-favourite-topics-static-latest-7502dcce7e26?gi=4b2eac00e85b)

So:

> “Static methods can be overloaded” means you can define multiple static methods with the same name but different parameters, and the compiler will resolve which one to call using the argument types (compile-time polymorphism).

### 5.2 Why static methods cannot be truly overridden

If a child class defines a static method with the same signature as in the parent:

- It is **method hiding**, not overriding.  
- The method called depends on the **reference type**, not the runtime object. [geeksforgeeks](https://www.geeksforgeeks.org/java/can-we-overload-or-override-static-methods-in-java/)

Example:

```java
class Parent {
    static void show() {
        System.out.println("Parent static show");
    }
}

class Child extends Parent {
    static void show() {
        System.out.println("Child static show");
    }
}

public class StaticHidingDemo {
    public static void main(String[] args) {
        Parent p = new Parent();
        Parent pc = new Child();
        Child c = new Child();

        p.show();   // Parent static show
        pc.show();  // Parent static show (reference type is Parent)
        c.show();   // Child static show
    }
}
```

No runtime polymorphism here:

- For static methods, **reference type** decides which method is called.  
- JVM doesn’t do dynamic dispatch for static methods.

So the correct interview wording:

- Static methods can be **overloaded**.  
- They cannot be **overridden** in the polymorphic sense; they are **hidden**.

***

## 6. Static Variables, Static Methods, Static Blocks, Static Nested Classes

These are all related topics that often appear with polymorphism and OOP questions.

### 6.1 Static variables (class-level fields)

- Belong to the **class**, not to instances.  
- One copy per class, shared by all objects. [runestone](https://runestone.academy/ns/books/published/csjava/Unit6-Writing-Classes/topic-6-7-static-vars-methods.html)

Example:

```java
class User {
    static int userCount = 0;  // static variable
    String name;

    User(String name) {
        this.name = name;
        userCount++;
    }
}

public class StaticVariableDemo {
    public static void main(String[] args) {
        User u1 = new User("A");
        User u2 = new User("B");

        System.out.println(User.userCount); // prints 2
    }
}
```

Interview points: [javahungry.blogspot](https://javahungry.blogspot.com/2022/11/interview-questions-static-keyword.html)

- All objects share `userCount`.  
- Access via `ClassName.variable` → `User.userCount`.  
- If one object modifies it, others see updated value.

### 6.2 Static methods (summary)

- Belong to the class; called via `ClassName.method()`.  
- Can only directly use static fields; no `this`, no instance fields. [medium](https://medium.com/@vino7tech/static-methods-static-blocks-and-static-variables-in-java-9f8be65edcef)
- **Can be overloaded**, not truly overridden (see section 5).

Common example: `public static void main(String[] args)`.

### 6.3 Static blocks

- `static { ... }` runs **once** when the class is loaded, before any object creation or `main()`. [blog.stackademic](https://blog.stackademic.com/tricky-core-java-interview-questions-interviewers-favourite-topics-static-latest-7502dcce7e26?gi=4b2eac00e85b)

Example:

```java
class Config {

    static String ENV;

    static {
        ENV = "PROD";
        System.out.println("Static block: ENV = " + ENV);
    }
}

public class StaticBlockDemo {
    public static void main(String[] args) {
        System.out.println("Main: ENV = " + Config.ENV);
    }
}
```

Used for:

- Complex initialization of static fields.  
- Loading configuration when the class is first loaded. [medium](https://medium.com/@vino7tech/static-methods-static-blocks-and-static-variables-in-java-9f8be65edcef)

### 6.4 Static nested classes

- A static class declared inside another class.  
- Does not need an instance of the outer class. [runestone](https://runestone.academy/ns/books/published/csjava/Unit6-Writing-Classes/topic-6-7-static-vars-methods.html)

Example:

```java
class Outer {

    static class Inner {
        static void show() {
            System.out.println("Inside static nested class");
        }
    }
}

public class StaticNestedDemo {
    public static void main(String[] args) {
        Outer.Inner.show();  // no Outer object needed
    }
}
```

Interview notes: [linkedin](https://www.linkedin.com/posts/lakshman-reddy_static-java-interview-activity-7285593203272028160-AVf6)

- Can access only static members of the outer class directly.  
- Useful to group helper types.

### 6.5 Constructors and `static`

- Constructors **cannot** be `static`. [scribd](https://www.scribd.com/document/923966681/java-unit-2)

Reasons:

- Constructors are per-object initialization logic (called with `new`).  
- `static` belongs to the **class**, not instances.  
- Class-level initialization is done via **static blocks**, not static constructors.

You can have:

- Static block for class-level setup.  
- Constructor for each new instance.

***

## 7. Interview-Focused Checklist for Polymorphism and Static

Concepts interviewers often test: [javahungry.blogspot](https://javahungry.blogspot.com/2022/11/polymorphism-interview-questions.html)

- Explain polymorphism with examples (overloading vs overriding).  
- Difference between compile-time and runtime polymorphism.  
- Can static methods be overloaded? Can they be overridden?  
- Can constructors be overloaded? (Yes.) Overridden? (No.)  
- How does JVM choose which overridden method to call at runtime?  
- Are instance variables polymorphic? (No, fields are resolved by reference type.)  
- What does the `static` keyword mean for variables, methods, blocks, classes?  
- Why is `main` static?  
- How many copies of a static variable exist across objects?

***
