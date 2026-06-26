# Java \& OOP Notes (Basics → OOP → For Spring Boot Prep)

## 1. Wrapper Classes

### 1.1 What are wrapper classes?

- Wrapper classes are **object versions of primitive types** in Java.[^1][^2][^3]
- They allow primitives to be used where **only objects are allowed** (e.g., collections, generics).[^2][^3]

Mapping:

- `boolean` → `Boolean`
- `byte` → `Byte`
- `short` → `Short`
- `int` → `Integer`
- `long` → `Long`
- `float` → `Float`
- `double` → `Double`
- `char` → `Character`


### 1.2 Why do wrapper classes exist?

1. **Collections require objects**

```
- `List<Integer>` is valid; `List<int>` is not.[^3][^2]
```

2. **Utility methods**
    - Parsing from `String` (e.g., `Integer.parseInt("123")`).[^1][^2]
    - Conversions and constants (e.g., `Integer.MAX_VALUE`).[^2][^1]
3. **Support for `null`**
    - `int` cannot be `null`; `Integer` can.[^4][^3]
    - Useful for nullable DB columns and JSON fields.

### 1.3 Wrapper classes in Spring / JPA

- Entities often use wrappers (`Integer`, `Long`) instead of primitives to allow `null` and to match DB nullable columns.[^5][^4]

**Question:**
> In a Spring Boot entity, for a nullable `age` column, should you use `int` or `Integer`, and why?

**Answer:**
Use `Integer` because it can store `null`, which maps to “no value” in the database, whereas `int` always has a value (default `0`).[^6][^4][^5]

***

## 2. Autoboxing and Unboxing

### 2.1 Definitions

- **Autoboxing**: automatic conversion from **primitive** to its **wrapper** type.[^7][^8][^9]
- **Unboxing**: automatic conversion from **wrapper** to its **primitive** type.[^8][^9][^7]

Example:

```java
int n = 10;
Integer obj = n;      // autoboxing (int → Integer)

Integer x = 20;
int y = x;           // unboxing (Integer → int)
```

Under the hood, the compiler inserts calls such as `Integer.valueOf(n)` and `obj.intValue()`.[^9][^10][^7]

### 2.2 Advantages of autoboxing

1. **Less boilerplate**
    - No need to manually call `valueOf()` or `xxxValue()`.[^10][^7][^8]
2. **Works seamlessly with collections**
    - Collections store objects, but you can write:

```java
List<Integer> list = new ArrayList<>();
list.add(10); // 10 autoboxed to Integer
```


[^7][^8][^9]

3. **Easier mixing between APIs**
    - One method might expect primitive, another wrapper; autoboxing/unboxing bridges them automatically.[^11][^12][^13]

### 2.3 Caveats

- **Performance cost**: autoboxing creates objects and adds GC pressure in tight loops.[^14][^11]
- **Null-safety**: unboxing `null` (e.g., `Integer obj = null; int n = obj;`) causes `NullPointerException`.[^13][^9][^10]

**Question:**
> In `List<Integer> nums = List.of(1, 2, 3);`, where does autoboxing happen?

**Answer:**
Each literal `1`, `2`, and `3` is a primitive `int` that is **autoboxed** to `Integer` when passed into `List.of(...)`.[^8][^9][^10]

***

## 3. Mutable vs Immutable

### 3.1 Definitions

- **Mutable object**: its state **can be changed** after creation.[^15][^16][^17]
- **Immutable object**: its state **cannot be changed** after creation; any “change” returns a **new object**.[^18][^19][^15]


### 3.2 Examples

- **Mutable**
    - `StringBuilder`, `ArrayList`, most custom classes with setters.[^16][^17]
    - You can modify them in place (e.g., `append`, `add`, `remove`).
- **Immutable**
    - `String`, `Integer`, `Long`, `LocalDate`, etc.[^19][^16][^18]
    - Methods that “change” them return **new instances**.

Example with `String`:

```java
String s = "hello";
s.toUpperCase();   // returns new String, original unchanged
```

If you want the new value:

```java
s = s.toUpperCase();  // now s refers to the new object
```


### 3.3 Why immutability matters

- **Thread safety**: immutable objects are safe to share between threads.[^20][^18]
- **Safe reuse**: can be cached and reused (e.g., String pool).[^21][^22]
- **Simpler reasoning**: no unexpected changes through other references.[^15][^20]

**Question:**
> If `String s = "hello";` and you call `s.toUpperCase();`, is `s` changed?

**Answer:**
No. `String` is immutable; `toUpperCase()` returns a new `String`. `s` still points to `"hello"` unless you assign the result back (`s = s.toUpperCase();`).[^23][^16][^21]

***

## 4. Strings and Memory

### 4.1 Where are strings stored?

In Java:

- The **reference** variable (e.g., `String s`) lives on the **stack** (inside the current method frame).[^24][^25]
- The actual **`String` object** is stored on the **heap**.[^26][^27][^28]
- **String literals** are stored in a special area of the heap called the **String Constant Pool**.[^22][^25][^29]

Example:

```java
String s = "hello";
```

- `"hello"` is created (or reused) in the string pool.[^30][^22]
- `s` points to that object.


### 4.2 Literal vs `new String`

```java
String a = "hello";
String b = "hello";
String c = new String("hello");
```

- `a` and `b` refer to the **same** pooled object `"hello"`.[^25][^22][^30]
- `c` refers to a **different** non-pooled `String` object created on the heap (even though content is `"hello"`).[^27][^28][^26]

**Question:**
> `String x = "java"; String y = "java";` – how many `String` objects are created?

**Answer:**
One pooled `String` object `"java"` is created (or reused) and both `x` and `y` refer to that same object.[^22][^25][^30]

***

## 5. `new` Keyword

### 5.1 What `new` does

- Allocates a **new object on the heap**.
- Calls the appropriate **constructor**.
- Returns a **reference** to the created object.[^31][^27]

Example:

```java
class User {
    String name;
}

User u = new User(); // creates a new User object
u.name = "Abhinav";
```

- `new User()` → object created on heap.
- `u` holds the reference.

In ATM or Calculator projects, every new account/session/calculator is created via `new`.

***

## 6. `this` Keyword

### 6.1 What `this` refers to

- `this` refers to the **current object instance** inside instance methods or constructors.[^32][^33]


### 6.2 Common uses

**a) Distinguishing fields from parameters**

```java
class User {
    private String name;

    User(String name) {
        this.name = name; // field = parameter
    }
}
```

**b) Calling another constructor**

```java
class User {
    String name;
    int age;

    User(String name) {
        this(name, 0); // calls the 2-arg constructor
    }

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```


***

## 7. Access Modifiers (Access Specifiers)

### 7.1 Overview

- **public**: accessible from **anywhere**.[^34][^35]
- **private**: accessible **only in the same class**.[^35][^34]
- **default (package-private)**: accessible in **same package only** (when no modifier is used).[^32][^35]
- **protected**: accessible in **same package** plus **subclasses in other packages**.[^36][^35][^32]

***

## 8. Default vs Protected

### 8.1 Default (package-private)

- Applied when **no access modifier** is specified.[^35][^32]
- Visibility:
    - Same class
    - Other classes in the **same package**
    - Not visible in other packages, even in subclasses.[^37][^38]

Example:

```java
class A {
    int x;           // default
    void foo() { }   // default
}
```


### 8.2 Protected

- Declared with `protected`.[^33][^36]
- Visibility:
    - Same class
    - Other classes in the **same package**
    - **Subclasses** in **other packages**.[^38][^36][^32]

Example:

```java
class A {
    protected int y;
    protected void bar() { }
}
```


### 8.3 Key difference

- `default`: **package-only** visibility.[^36][^37]
- `protected`: package visibility **plus** subclasses outside the package.[^37][^38][^36]

**Question:**
> If `Base` (package `p1`) has `protected int value;`, can a subclass `Child` in another package `p2` access `value`?

**Answer:**
Yes. Because `value` is `protected`, it is accessible in subclasses even if they are in a different package.[^38][^32][^36]

***

## 9. Getters and Setters

### 9.1 Why getters and setters?

- Encapsulate fields and control access.[^39][^40][^41]
- Let you add validation, logging, and keep internal representation flexible.[^42][^43]

Example:

```java
class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }

    public int getAge() {
        return age;
    }
}
```


### 9.2 Why do setters often seem “not called explicitly”?

1. **Constructors or builders set values instead**.[^44][^42]
2. **Frameworks (Spring, Jackson, JPA) call setters via reflection** when mapping JSON/body/DB row to objects.[^40][^42]
3. **Immutable style**: sometimes no setters exist; everything is set via constructors.[^43][^42]

**Question:**
> Who usually calls `setName` for a Spring Boot DTO/entity when JSON like `{ "name": "Abhinav" }` is sent?

**Answer:**
The **framework** (Spring/Jackson) calls `setName` via reflection when binding the JSON to the Java object; you typically do not call it manually.[^40][^42]

***

