import java.util.*;
import java.util.function.BiPredicate;

public class ExpressionBoolean {

    // Define the comparison operators
    static Map<String, BiPredicate<Integer, Integer>> comparisonOperators = new HashMap<>() {{
        put("<", (o1, o2) -> o1 < o2);
        put(">", (o1, o2) -> o1 > o2);
        put("=", Integer::equals);
        put("<>", (o1, o2) -> !o1.equals(o2));
    }};

    // Define the logical operators
    static Map<String, BiPredicate<Boolean, Boolean>> logicalOperators = new HashMap<>() {{
        put("and", (o1, o2) -> o1 && o2);
        put("or", (o1, o2) -> o1 || o2);
    }};

    /**
     * Executes a boolean expression based on the provided tokens.
     *
     * @param tokens The boolean expression split into tokens.
     * @return The result of the evaluated expression.
     */
    public static boolean executeExpression(String[] tokens) throws CompilationError {
        Stack<Boolean> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        // Define operator precedence
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("<>", 3);
        precedence.put(">", 3);
        precedence.put("<", 3);
        precedence.put("=", 3);
        precedence.put("and", 2);
        precedence.put("or", 1);

        int i = 0;
        while (i < tokens.length) {
            String token = tokens[i].toLowerCase();

            if (token.equals("(")) {
                operatorStack.push(token);
                i++;
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    applyTopOperator(operandStack, operatorStack);
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                    operatorStack.pop();
                } else {
                    throw new CompilationError("Mismatched parentheses");
                }
                i++;
            } else if (isLogicalOperator(token)) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")
                        && precedence.getOrDefault(operatorStack.peek(), 0) >= precedence.get(token)) {
                    applyTopOperator(operandStack, operatorStack);
                }
                operatorStack.push(token);
                i++;
            } else if (isComparisonOperator(token)) {
                // Expecting a comparison: left operand, operator, right operand
                if (i == 0 || i == tokens.length - 1) {
                    throw new CompilationError("Invalid comparison operator position");
                }
                String leftToken = tokens[i - 1];
                String operator = token;
                String rightToken = tokens[i + 1];

                boolean comparisonResult = evaluateComparison(leftToken, operator, rightToken);
                operandStack.push(comparisonResult);

                // Skip the next token as it's part of the comparison
                i += 2;

                // Remove the left operand as it has been processed
                // This assumes that the left operand was pushed as a boolean
                // If it's part of a larger expression, handle accordingly
                if (!operatorStack.isEmpty() && isComparisonOperator(operatorStack.peek())) {
                    // Do nothing
                }
            } else {
                // It's either a boolean variable or part of a comparison (handled above)
                // To avoid duplicate processing, check if next token is a comparison operator
                if (i + 1 < tokens.length && isComparisonOperator(tokens[i + 1])) {
                    // The comparison will be handled in the next iteration
                    i++;
                } else {
                    // It's a standalone boolean variable
                    boolean value = Runner.boolStack.peek().getValue(tokens[i]);
                    operandStack.push(value);
                    i++;
                }
            }
        }

        // Apply remaining operators
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().equals("(") || operatorStack.peek().equals(")")) {
                throw new CompilationError("Mismatched parentheses");
            }
            applyTopOperator(operandStack, operatorStack);
        }

        if (operandStack.size() != 1) {
            throw new CompilationError("Invalid expression");
        }

        return operandStack.pop();
    }

    /**
     * Applies the top operator from the operator stack to the top operands in the operand stack.
     *
     * @param operandStack The stack containing boolean operands.
     * @param operatorStack The stack containing operators.
     */
    private static void applyTopOperator(Stack<Boolean> operandStack, Stack<String> operatorStack) throws CompilationError {
        if (operandStack.size() < 2) {
            throw new CompilationError("Insufficient operands");
        }
        String operator = operatorStack.pop();
        boolean right = operandStack.pop();
        boolean left = operandStack.pop();

        BiPredicate<Boolean, Boolean> operation = logicalOperators.get(operator);
        if (operation == null) {
            throw new CompilationError("Unsupported operator: " + operator);
        }
        boolean result = operation.test(left, right);
        operandStack.push(result);
    }

    /**
     * Evaluates a comparison between two operands.
     *
     * @param leftToken The left operand (variable name or integer).
     * @param operator The comparison operator.
     * @param rightToken The right operand (variable name or integer).
     * @return The result of the comparison.
     */
    private static boolean evaluateComparison(String leftToken, String operator, String rightToken) throws CompilationError {
        Integer leftValue = getIntegerValue(leftToken);
        Integer rightValue = getIntegerValue(rightToken);

        BiPredicate<Integer, Integer> compOp = comparisonOperators.get(operator);
        if (compOp == null) {
            throw new CompilationError("Unsupported comparison operator: " + operator);
        }
        return compOp.test(leftValue, rightValue);
    }

    /**
     * Retrieves the integer value of a token, either directly or from Runner.ints.
     *
     * @param token The token representing an integer or variable.
     * @return The integer value.
     */
    private static Integer getIntegerValue(String token) throws CompilationError {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            // Assume it's a variable
            Integer value = Runner.intStack.peek().getValue(token);
            if (value == null) {
                throw new CompilationError("Undefined integer variable: " + token);
            }
            return value;
        }
    }

    /**
     * Checks if a token is a comparison operator.
     *
     * @param token The token to check.
     * @return True if it's a comparison operator, false otherwise.
     */
    private static boolean isComparisonOperator(String token) {
        return comparisonOperators.containsKey(token);
    }

    /**
     * Checks if a token is a logical operator.
     *
     * @param token The token to check.
     * @return True if it's a logical operator, false otherwise.
     */
    private static boolean isLogicalOperator(String token) {
        return logicalOperators.containsKey(token);
    }

}
