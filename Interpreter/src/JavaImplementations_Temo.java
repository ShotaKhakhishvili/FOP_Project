public class JavaImplementations_Temo {

    /**
     * Task 1: Sum of First N Numbers
     * This method calculates the sum of the first N natural numbers.
     * The formula used is: sum = n * (n + 1) / 2
     *
     * @param n The number up to which the sum is calculated. Must be a non-negative integer.
     */

    public void task1(int n) {
        int sum = n * (n + 1) / 2;  // Efficient computation of the sum of the first N natural numbers
        System.out.println(sum); // Outputs the result
    }

    /**
     * Task 2: Factorial of N
     * This method calculates the factorial of a given non-negative integer N.
     * The factorial is defined as the product of all positive integers from 1 to N.
     *
     * @param n The number for which the factorial is calculated. Must be a non-negative integer.
     */

    public void task2(int n) {
        int res = 1; // Initialize the result to 1, as factorial computation starts from 1
        while (n > 0) {
            res *= n;
            n--;
        }
        System.out.println(res);
    }

    /**
     * Task 3: GCD of Two Numbers
     * This method calculates the Greatest Common Divisor (GCD) of two positive integers using the Euclidean algorithm.
     * The GCD is the largest positive integer that divides both numbers without leaving a remainder.
     *
     * @param a The first positive integer.
     * @param b The second positive integer.
     * @throws RuntimeException if either input is not a positive integer.
     */

    public void task3(int a, int b) {
        if (a <= 0) {
            throw new RuntimeException("Invalid input");
        }
        if (b <= 0) {
            throw new RuntimeException("Invalid input");
        } // Ensure both inputs are positive integers

        while (b > 0) {  // Euclidean algorithm implementation
            int temp = b;
            b = a % b;
            a = temp;
        }

        System.out.println(a);
    }


    /**
     * Task 4: Reverse a Number
     * This method reverses the digits of a given positive integer.
     *
     * @param n The number to be reversed. Must be a non-negative integer.
     */

    public void task4(int n) {
        int reversed = 0;
        while (n > 0) {
            int dig = n % 10;
            reversed *= 10;
            reversed += dig; // Append the digit to the reversed number
            n /= 10;
        }

        System.out.println(reversed);
    }


    /**
     * Task 7: Find the Largest Digit in a Number
     * This method identifies the largest digit in a given positive integer.
     *
     * @param n The number from which the largest digit is to be found. Must be a non-negative integer.
     */

    public void task7(int n) {
        int maxDigit = 0;

        while (n > 0) {
            int digit = n % 10; // Extract the last digit of the number
            maxDigit = Math.max(digit, maxDigit); // Update maxDigit if the current digit is greater
            n /= 10; // Remove the last digit from the number
        }
        System.out.println(maxDigit);
    }

    /**
     * Task 8: Sum of Digits
     * This method calculates the sum of all digits in a given positive integer.
     *
     * @param n The number whose digits are to be summed. Must be a non-negative integer.
     */

    public void task8(int n) {
        int sum = 0;

        while (n > 0) {
            sum += n % 10; // Add the last digit of the number to the sum
            n /= 10; // Remove the last digit from the number
        }

        System.out.println(sum);
    }


    /**
     * Task 9: Multiplication Table
     * This method prints the multiplication table for a given integer from 1 to 10.
     *
     * @param n The number whose multiplication table is to be printed.
     */

    public void task9(int n) {
        int i = 1;
        while (i < 11) {
            System.out.println(n * i); // Print the product of n and the current counter
            i++; // And then increment
        }
    }

    /**
     * Task 10: Nth Fibonacci Number
     * This method calculates the Nth Fibonacci number using an iterative approach.
     * The Fibonacci sequence starts with 1, 1, and each subsequent number is the sum of the previous two.
     *
     * @param n The position in the Fibonacci sequence to compute. Must be a positive integer.
     */

    public void task10(int n) {

        if (n == 1 || n == 2) {
            System.out.println(1);
            return;
        } // Handle base cases directly

        int a = 1;
        int b = 1;
        int i = 3;
        int fib = 0;

        while (i <= n) {  // Loop should run until i is n, not n+1
            fib = a + b;
            a = b;
            b = fib;
            i++;
        }

        System.out.println(fib);
    }

}
