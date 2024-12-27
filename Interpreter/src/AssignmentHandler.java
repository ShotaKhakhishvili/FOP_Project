public class AssignmentHandler {

    /**
     * Executes an assignment operation by evaluating an expression and assigning the result to a variable.
     *
     * @param args The arguments passed for the assignment. The first argument is the variable name, the second argument is the operator, and the rest are the expression components.
     * @throws CompilationError if there is an error during the execution of the expression.
     */
    static void executeAssignment(String[] args) throws CompilationError {
        // Prepare an array to hold the parts of the expression, excluding the first two arguments
        String[] expressionArgs = new String[args.length - 2];

        // Populate the expressionArgs array starting from the third element in args
        for (int i = 2; i < args.length; i++) {
            expressionArgs[i - 2] = args[i];
        }

        // Evaluate the expression and assign the result to the variable identified by args[0]
        Runner.intStack.peek().setValue(args[0], ExpressionInteger.executeExpressionInteger(expressionArgs));
    }

    /**
     * Checks whether a given string is a numeric value.
     *
     * @param str The string to check.
     * @return True if the string represents a number, false otherwise.
     */
    public static boolean isNumber(String str) {
        // Iterate through each character in the string
        for (char ch : str.toCharArray()) {
            // If the character is not a digit, return false
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
        // If all characters are digits, return true
        return true;
    }
}
