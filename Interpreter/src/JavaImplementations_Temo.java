public class JavaImplementations_Temo {

        //Sum of First N Numbers
        public void task1(int n){
            int sum = n * (n + 1) / 2;
            System.out.println(sum);
        }


        //Factorial of N
        public void task2(int n){
            int res = 1;
            while(n > 0){
                res *= n;
                n--;
            }
            System.out.println(res);
        }

        //GCD of Two Numbers
        public void task3(int a, int b){
            if(a <= 0){
                throw new RuntimeException("Invalid input");
            }
            if(b <= 0){
                throw new RuntimeException("Invalid input");
            }

            while(b > 0){
                int temp = b;
                b = a % b;
                a = temp;
            }

            System.out.println(a);
        }

        //Reverse a Number
        public void task4(int n){
            int reversed = 0;
            while(n > 0){
                int dig = n % 10;
                reversed *= 10;
                reversed += dig;
                n /= 10;
            }

            System.out.println(reversed);
        }



        //Find the Largest Digit in a Number
        public void task7(int n){
            int maxDigit = 0;

            while(n > 0){
                int digit = n % 10;
                maxDigit = Math.max(digit, maxDigit);
                n /= 10;
            }
            System.out.println(maxDigit);
        }

        //Sum of Digits
        public void task8(int n){
            int sum = 0;

            while(n > 0){
                sum += n % 10;
                n /= 10;
            }

            System.out.println(sum);
        }


        // Multiplication Table
        public void task9(int n){
            int i = 1;
            while(i < 11){
                System.out.println(n * i);
                i++;
            }
        }

        //Nth Fibonacci Number
        public void task10(int n){
            int a = 1;
            int b = 1;
            int i = 2;
            int fib = 0;

            while(i <= n){
                fib = a + b;
                b = fib;
                a = b;
                i++;
            }

            System.out.println(fib);
        }

}
