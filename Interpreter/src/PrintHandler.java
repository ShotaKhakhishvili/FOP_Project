

/**
 * @PrintHandler class -
 *
 * Handles the execution of print statements in the interpreter. It processes both -- string-based and expression-based
 * print messages, evaluates expressions when needed, and formats the final message for output.
 */
public class PrintHandler {




    /**
     * Executes the print statement.
     * @param args - Arguments for the print statement.
     * @param isString - Flag indicating if the message is a string.
     * @throws CompilationError - If there is an error in processing the print.
     */
    public static void executePrint(String[] args, boolean isString) throws CompilationError {

        // Print the final output message to the console
        System.out.println("PROGRAM OUTPUT: " + getPrintMessage(args, isString));
    }




    /**
     * Constructs the print message based on the arguments.
     * @param args - Arguments for the print statement.
     * @param isString - Flag indicating if the message is a string.
     * @return - The message to be printed.
     * @throws CompilationError - If there is an error in processing the message.
     */
    public static String getPrintMessage(String[] args, boolean isString) throws CompilationError {

        if(isString) return getStringPrintMessage(); // Handle string message

        String[] expressionArgs = new String[args.length - 1];

        for(int i = 1; i < args.length; i++){
            expressionArgs[i-1] = args[i]; // Copy expression arguments
        }

        return Integer.toString(ExpressionInteger.executeExpressionInteger(expressionArgs));
    }



    /**
     * Extracts and processes the string message for the print statement.
     * @return - The string message to be printed.
     * @throws CompilationError - If there is an invalid string format.
     */
    private static String getStringPrintMessage() throws CompilationError {

        StringBuilder current = new StringBuilder(); // To store the current segment of the message
        boolean string = false; // Tracks if we are inside a string
        StringBuilder answer = new StringBuilder();  // Final processed message

        // Get the current line of code and convert it to a character array
        String stringLine = Runner.lines[Runner.pc];
        char[] line = stringLine.toCharArray();

        // Check the number of quotes to ensure the string is valid
        int i = 0;
        for(char ch : line){
           if(ch == '"') i++; // Count the quotes
        }

        // If the number of quotes is odd, it indicates an invalid string
        if(i % 2 == 1)
            throw new CompilationError("Invalid String Value Inside Print Statement");

        // Skip to the content part after the 'print' keyword
        i = 0;
        while (line[i] != 't') i++; // Skip to the 't' in 'print'


        //==========================================================================================
        // Process each character in the line
        for(i++; i < line.length; i++){


            if(string){
                // If inside a string, append characters until the closing quote
                if(line[i] == '"'){
                    answer.append(current); // Add the current string segment to the answer
                    current = new StringBuilder(); // Reset for the next segment
                    string = false; // Exit the string mode
                    continue;
                }
                current.append(line[i]); // Add the character to the current string segment
                continue;
            }



            if(line[i] == ' ' || line[i] == '"'){
                // Handle spaces or the start of a string
                if(!current.isEmpty()){
                    // If there's a variable, get its value and add it to the answer
                    String val = String.valueOf(Variables.getIntegerValue(String.valueOf(current)));
                    answer.append(val);
                }
                current = new StringBuilder(); // Reset for the next segment
                if(line[i] == '"') string = true; // Enter string mode
                continue;
            }



            if(InstructionHandler.invalidChars.contains(line[i])){
                // If an invalid character is found, throw an error
                throw new CompilationError("Invalid Print Statement");
            }



            current.append(line[i]); // Add the valid character to the current segment
        }
        //==========================================================================================

        // If there's any remaining segment, process it as a variable
        if(!current.isEmpty()){
            String val = String.valueOf(Variables.getIntegerValue(String.valueOf(current)));
            answer.append(val);
        }


        // Return the fully processed message
        return answer.toString();
    }
}
