public class JavaImplementations_Shota{

    // Implementation of the algorithm 5 in java
    public void task5(int n){
        boolean answer = true;
        int i = 2; // starting iteration from number 2 up to number n - 1
        while(i < n){
            if(n % i == 0){ // if at some point, num is divisible my i, then it is not prime
                answer = false;
            }
            i++;
        }

        System.out.println(answer);
    }

    // Implementation of the algorithm 6 in java
    public void task6(int n){
        // We reverse num and then compare num and reversed number.
        int reversed = 0;
        int t2 = n;

        while(t2 > 0){
            int t = t2 % 10;
            reversed = reversed + t;
            t2 = t2 / 10;
            if(t2 > 0){
                reversed = reversed * 10;
            }
        }

        boolean answer = false;

        if(reversed == n) // if the reversed integer is equal to the given integer, then it is a palidrome
            answer = true;

        System.out.println(answer);
    }
}
