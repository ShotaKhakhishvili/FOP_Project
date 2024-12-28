public class JavaImplementations_Misha {

    // Method to reverse the digits of a given number
    public void task4(int number) {
        // Variable to store the reversed number
        int answer = 0;
        // Temporary variable to manipulate the number
        int number_temp = number;

        // Loop to extract and reverse the digits of the number
        while (number_temp != 0) {
            // Append the last digit of number_temp to answer
            answer = answer * 10 + number_temp % 10;
            // Remove the last digit from number_temp
            number_temp = number_temp / 10;
        }

        // Print the reversed number
        System.out.println(answer);
    }

    // Method to find the largest digit in a given number
    public void task7(int number) {
        // Variable to store the largest digit found
        int answer = 0;
        // Temporary variable to manipulate the number
        int number_temp = number;

        // Loop to extract each digit and compare it to the current largest digit
        while (number_temp != 0) {
            // Extract the last digit of number_temp
            int digit = number_temp % 10;
            // Check if the current digit is greater than the current largest digit
            if (digit > answer) {
                // Update the largest digit
                answer = digit;
            }
            // Remove the last digit from number_temp
            number_temp = number_temp / 10;
        }
        // Print the largest digit
        System.out.println(answer);
    }
}
