public class JavaImplementations_Shota{

    // Implementation of the algorithm 5 in java
    public void task5(int n){
        boolean answer = true;
        int i = 2; // starting iteration from number 2 up to number n - 1
        while(i < n){
            int t = n % i;
            if(t == 0){ // if at some point, num is divisible my i, then it is not prime
                answer = false;
            }
            i++;
        }

        System.out.println(answer);
    }

    // Implementation of the algorithm 6 in java
    public static void task6(int n){
        boolean answer = true;
        while(n > 9){
            int temp = n;
            int power = 1;
            while(temp > 10){
                temp /= 10;
                power *= 10;
            }

            if(temp != n % 10)
                answer = false;

            n -= temp * power + n % 10;
            n /= 10;
        }

        System.out.println(answer);
    }
}
