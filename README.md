# BASIC to Java Interpreter

This project is an interpreter that translates BASIC programming language code snippets into Java, compiles them, and executes the resulting Java code. Below is a detailed explanation of the features supported by the interpreter, including usage examples and functionality.

---

## Features

### **Variable Declaration**
The interpreter supports variable declaration for two data types: `integer` and `boolean`.

#### **Syntax:**
```
Dim [name] As [type]
```

#### **Examples:**
- Declare an integer:
  ```
  Dim x As Integer
  ```
  Translated Java:
  ```java
  int x = 0;
  ```

- Declare a boolean:
  ```
  Dim isValid As Boolean
  ```
  Translated Java:
  ```java
  boolean isValid = false;
  ```

---

### **Variable Assignment**
The interpreter supports assignments for both `boolean` and `integer` types.

#### **Boolean Assignment**

- Assign a constant value:
  ```
  isValid = true
  ```
  Translated Java:
  ```java
  isValid = true;
  ```

- Assign a boolean expression:
  ```
  isValid = isTrue and isVerified
  ```
  Translated Java:
  ```java
  isValid = isTrue && isVerified;
  ```

NOTE: Boolean expressions don't support arithmetic operations inside them.

#### **Integer Assignment**

- Assign an arithmetic expression:
  ```
  x = 5 + y
  ```
  Translated Java:
  ```java
  x = 5 + y;
  ```

#### **Supported Boolean Expressions:**
- Expressions with `and`/`or`:
  ```
  isValid = isReady or isAvailable
  ```
  Translated Java:
  ```java
  isValid = isReady || isAvailable;
  ```

#### **Supported Arithmetic Expressions:**
- Use arithmetic operators (+, -, *, /, %):
  ```
  x = a + b * c
  ```
  Translated Java:
  ```java
  x = a + b * c;
  ```

---

### **Control Structures**

#### **While Loop**
Supports loops with a boolean expression as a condition.

#### **Syntax:**
```
while [boolean expression]
...
[statements]
...
wend
```

#### **Example:**
BASIC:
```
while x < 10
  x = x + 1
  print x
wend
```
Translated Java:
```java
while (x < 10) {
  x = x + 1;
  System.out.println(x);
}
```

---

#### **If Statements**
Supports conditional statements.

#### **Syntax:**
```
if [boolean expression] then
...
[statements]
...
end if
```

#### **Example:**
BASIC:
```
if x > 5 then
  print "x is greater than 5"
end if
```
Translated Java:
```java
if (x > 5) {
  System.out.println("x is greater than 5");
}
```

---

### **String Operations When Printing**

#### **String Constants:**
String constants are enclosed in double quotes:
```
"Hello, World!"
```

#### **String Statements:**
You can concatenate strings and variables:
```
"Hello, " userName
```

Translated Java:
```java
"Hello, " + userName;
```

---

### **Data Type Conversions**
- **Integer to Characters:** Converts all digits of an integer to their character representation.
- **Boolean to Integer:** Converts `true` to `1` and `false` to `0`.

#### **Example:**
BASIC:
```
Dim num As Integer
num = 42
print "your number is: " num
```

Translated Java:
```java
int num = 42;
System.out.println("your number is: " + num);
```


### **Arithmetic Operation Support**
- ** When porinting, you can also use arithmetic operations when there is no boolean expression or string.

BASIC:
```
Dim num As Integer
num = 42
print (num + 12) / 2
```

Translated Java:
```java
int num = 42;
System.out.println((num + 12) / 2);
```

---

### **Supported Statements**
The interpreter supports the following statements:

1. **Declaration**:
   ```
   Dim x As Integer
   Dim isReady As Boolean
   ```

2. **Assignment**:
   ```
   x = 5 + 10
   isReady = true
   ```

3. **While Loop**:
   ```
   while x < 10
     x = x + 1
   wend
   ```

4. **If-Then Statement**:
   ```
   if x > 5 then
     print "Greater"
   end if
   ```

5. **Print Statement**:
   ```
   print "Hello, World!"
   ```

6. **Input Statement**:
   ```
   input x
   ```

---

## How To Test

1. We have pepared 10 algorithms in .txt format inside CodesInBASIC directory, inside Interpreter directory.
2. Run the main class (`Main.java`) to start the compilation of those 10 algorithms one by one.
3. You can also add your own .txt file and pass its name as a program argument to main, so it can run it.
4. If you want to test our pre-written algorithms, don't delete any of them nor change their names, or else the Java program won't be able to run properly.

---

## Example Algorithms Supported by the Interpreter

The interpreter is capable of executing the following BASIC algorithms:

### 1. **Sum of All Positive Numbers**
This computes the sum of all positive integers up to a given number.

---

### 2. **Factorial Calculation**
This calculates the factorial of a given number.

---

### 3. **Greatest Common Divisor (GCD)**
This determines the GCD of two numbers using the Euclidean algorithm.

---

### 4. **Digit Reversal**
This reverses the digits of a number.

---

### 5. **Prime Number Check**
This checks whether a number is prime.

---

### 6. **Palindrome Check**
This checks if a number is a palindrome.

---

### 7. **Maximum Digit in a Number**
This finds the largest digit in a number.

---

### 8. **Sum of Digits**
This calculates the sum of all digits in a number.

---

### 9. **Multiplication Table**
This generates a multiplication table for a given number.

---

### 10. **Fibonacci Number Calculation**
This computes the nth Fibonacci number.

---
