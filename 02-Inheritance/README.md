# Java Inheritance Notes (Superclass, Subclass, `super`, Types, Diamond Problem)

## 1. Inheritance, Superclass, Subclass, Child/Derived/Extended Class

### 1.1 What is inheritance?

Inheritance is when one class **reuses and extends** another class’s fields and methods. The new class automatically gets the parent’s behavior and can add or override behavior. [geeksforgeeks](https://www.geeksforgeeks.org/cpp/what-is-inheritance-1/)

Think:  

> `Car` **is a** `Vehicle` → `class Car extends Vehicle { }`.

Why it’s useful:

- Avoids code duplication.  
- Expresses “is-a” relationship.  
- Lets you specialize a general concept (e.g., `Vehicle` → `Car`, `Bus`). [medium](https://medium.com/@hasamuddin.afz/understanding-inheritance-in-object-oriented-programming-073d069a6d90)

### 1.2 Superclass / Base / Parent class

All these terms mean the same thing:

- **Superclass**
- **Base class**
- **Parent class**

**Definition:**  
The class that is **being inherited from**. It provides common properties and behaviors. [andrew.cmu](https://www.andrew.cmu.edu/course/15-121/lectures/Inheritance/inheritance.html)

Example:

```java
class Animal {              // superclass / base / parent
    String name;

    void eat() {
        System.out.println(name + " is eating.");
    }
}
```

### 1.3 Subclass / Derived / Child / Extended class

These terms also mean the same thing:

- **Subclass**
- **Derived class**
- **Child class**
- **Extended class**

**Definition:**  
The class that **inherits from another class** using `extends`. It gets all accessible members of the superclass and can add its own. [whitman](https://www.whitman.edu/mathematics/java_tutorial/java/javaOO/subclasses.html)

Example:

```java
class Dog extends Animal {   // subclass / derived / child / extended
    void bark() {
        System.out.println(name + " is barking.");
    }
}
```

### 1.4 Complete example with `main()`

```java
class Animal {                // Superclass / Base / Parent class
    String name;

    void eat() {
        System.out.println(name + " is eating.");
    }
}

// Dog is a subclass / child / derived / extended class of Animal
class Dog extends Animal {    // Subclass / Derived / Child / Extended class
    void bark() {
        System.out.println(name + " is barking.");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        // Creating a Dog object (child class)
        Dog dog = new Dog();

        // 'name' is inherited from Animal (superclass)
        dog.name = "Buddy";

        // Calling inherited method from Animal
        dog.eat();               // uses Animal.eat()

        // Calling Dog's own method
        dog.bark();              // uses Dog.bark()
    }
}
```

In words:

- `Animal` is the **superclass**.  
- `Dog` is the **subclass** that inherits from `Animal`.  
- `Dog` **is an** `Animal` (is-a relationship). [geeksforgeeks](https://www.geeksforgeeks.org/java/inheritance-in-java/)

***

## 2. `super` Keyword

### 2.1 Intuition

`super` means: “**go to the parent (superclass) version**”.

You use `super` in a subclass to: [geeksforgeeks](https://www.geeksforgeeks.org/java/super-keyword/)

1. Call the **superclass constructor** (`super(...)`).  
2. Call a **superclass method** from an overridden method.  
3. Access a **superclass field** when it’s hidden by a field with the same name.

### 2.2 Using `super()` to call superclass constructor

```java
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor called for: " + name);
    }
}

class Dog extends Animal {

    Dog(String name) {
        super(name); // calls Animal(String name)
        System.out.println("Dog constructor called for: " + name);
    }
}

public class SuperConstructorDemo {
    public static void main(String[] args) {
        Dog d = new Dog("Buddy");
    }
}
```

Key points: [programiz](https://www.programiz.com/java-programming/super-keyword)

- `super(name)` must be the **first statement** in the child’s constructor.  
- Ensures the parent part (`Animal`) is initialized before the child (`Dog`).

### 2.3 Using `super` to call superclass methods

```java
class Account {
    double balance;

    void showBalance() {
        System.out.println("Balance: " + balance);
    }
}

class SavingsAccount extends Account {

    @Override
    void showBalance() {
        System.out.println("Savings Account:");
        super.showBalance(); // call parent version
    }
}

public class SuperMethodDemo {
    public static void main(String[] args) {
        SavingsAccount sa = new SavingsAccount();
        sa.balance = 1000;
        sa.showBalance();
    }
}
```

- `super.showBalance()` calls the **inherited** method from `Account`.  
- Useful when you override a method but still want the original behavior. [docs.oracle](https://docs.oracle.com/javase/tutorial/java/IandI/super.html)

### 2.4 Using `super` to access superclass fields

```java
class Parent {
    String name = "Parent";
}

class Child extends Parent {
    String name = "Child";

    void printNames() {
        System.out.println(name);        // Child
        System.out.println(super.name);  // Parent
    }
}

public class SuperFieldDemo {
    public static void main(String[] args) {
        Child c = new Child();
        c.printNames();
    }
}
```

- `super.name` accesses the parent’s `name` when both have a field with the same identifier. [geeksforgeeks](https://www.geeksforgeeks.org/java/super-keyword/)

***

## 3. Types of Inheritance (Classes + Interfaces)

In Java with **classes**, the main forms are: [medium](https://medium.com/@quipoin04/types-of-inheritance-in-java-explained-with-examples-fa48fad62576)

- Single inheritance  
- Multilevel inheritance  
- Hierarchical inheritance  

Multiple and hybrid inheritance show up via **interfaces**.

### 3.1 Single Inheritance

One parent → one child (A → B).

```java
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}

public class SingleInheritanceDemo {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.eat();   // inherited from Animal
        d.bark();  // from Dog
    }
}
```

### 3.2 Multilevel Inheritance

Chain: A → B → C.

```java
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}

class Puppy extends Dog {
    void weep() {
        System.out.println("Puppy is weeping");
    }
}

public class MultilevelInheritanceDemo {
    public static void main(String[] args) {
        Puppy p = new Puppy();
        p.eat();   // from Animal
        p.bark();  // from Dog
        p.weep();  // from Puppy
    }
}
```

### 3.3 Hierarchical Inheritance

One parent → multiple children (A → B, A → C).

```java
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}

class Cat extends Animal {
    void meow() {
        System.out.println("Cat is meowing");
    }
}

public class HierarchicalInheritanceDemo {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.eat();
        d.bark();

        Cat c = new Cat();
        c.eat();
        c.meow();
    }
}
```

### 3.4 Multiple inheritance via interfaces

Classes cannot extend multiple classes, but they can implement multiple interfaces. [scaler](https://www.scaler.com/topics/why-multiple-inheritance-is-not-supported-in-java/)

```java
interface Runner {
    void run();
}

interface Swimmer {
    void swim();
}

class Athlete implements Runner, Swimmer {
    public void run() {
        System.out.println("Athlete is running");
    }

    public void swim() {
        System.out.println("Athlete is swimming");
    }

    public static void main(String[] args) {
        Athlete a = new Athlete();
        a.run();
        a.swim();
    }
}
```

- This is “multiple inheritance of **type/behavior**” via interfaces, without multiple parent classes. [stackoverflow](https://stackoverflow.com/questions/2515477/why-is-there-no-multiple-inheritance-in-java-but-implementing-multiple-interfac)

***

## 4. Why Java Prohibits Multiple Inheritance of Classes

### 4.1 Diamond Problem with classes

Consider:

```text
      A
     / \
    B   C
     \ /
      D
```

- `A` defines `void show()`.  
- `B` and `C` both override `show()`.  
- If `D` could extend both `B` and `C`, `d.show()` would be **ambiguous**: should it use `B.show()` or `C.show()`? [geeksforgeeks](https://www.geeksforgeeks.org/java/why-java-doesnt-support-multiple-inheritance/)

Java avoids this complexity by:

- Forbidding a class from extending more than one class (no `class D extends B, C`). [medium](https://medium.com/codex/why-java-does-not-support-multiple-inheritance-d05b596d8433)
- This keeps method resolution simple and predictable.

### 4.2 Other design reasons

Multiple inheritance of classes can: [medium](https://medium.com/@prasad1/why-java-does-not-support-multiple-inheritance-749addeaecfd)

- Make hierarchies hard to maintain and understand.  
- Increase the chance of conflicts when parent classes evolve.  
- Complicate the language and compiler.

So the design rule:

> A class can only `extends` **one** class in Java.

***

## 5. How Interfaces Solve the Diamond Problem

### 5.1 Classic interfaces (pre-Java 8)

Interfaces originally contained only **method declarations** (no implementation), so no diamond ambiguity existed: there was always a single implementation in the class. [scaler](https://www.scaler.com/topics/why-multiple-inheritance-is-not-supported-in-java/)

```java
interface A {
    void show();
}

interface B extends A { }

interface C extends A { }

class D implements B, C {
    @Override
    public void show() {
        System.out.println("D's show");
    }
}
```

- `A` only declares `show()`.  
- `D` provides the only implementation. There’s no conflict. [medium](https://medium.com/codex/why-java-does-not-support-multiple-inheritance-d05b596d8433)

### 5.2 Java 8+ default methods and diamond

With default methods, interfaces can have implementations, which can create a diamond-like structure. Java requires **explicit resolution**. [medium](https://medium.com/@vusal.guliyev.313/javas-diamond-problem-understanding-and-resolving-8a88869371e5)

```java
interface A {
    default void show() {
        System.out.println("From A");
    }
}

interface B extends A {
    default void show() {
        System.out.println("From B");
    }
}

interface C extends A {
    default void show() {
        System.out.println("From C");
    }
}

class D implements B, C {
    @Override
    public void show() {
        // must resolve explicitly
        B.super.show();  // or C.super.show();
        System.out.println("From D");
    }

    public static void main(String[] args) {
        D obj = new D();
        obj.show();
    }
}
```

Rules: [geeksforgeeks](https://www.geeksforgeeks.org/java/diamond-problem-in-java/)

- If a class inherits conflicting default methods from multiple interfaces, the compiler **forces** you to override the method.  
- Inside the override, you can call a specific interface’s version via `InterfaceName.super.methodName()`.

This solves the diamond problem by making the class (e.g., `D`) explicitly choose which implementation to use, rather than letting ambiguity silently exist.

***