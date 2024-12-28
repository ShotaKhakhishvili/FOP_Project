import java.util.*;
import java.util.function.BiFunction;

public class ExpressionInteger {

    // Define the arithmetic operators with their corresponding BiFunction
    static Map<String, BiFunction<Integer, Integer, Integer>> arithmeticOperators = new HashMap<>() {{
        put("+", (a, b) -> a + b);
        put("-", (a, b) -> a - b);
        put("*", (a, b) -> a * b);
        put("/", (a, b) -> {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        });
        put("%", (a, b) -> {
            if (b == 0) throw new ArithmeticException("Modulo by zero");
            return a % b;
        });
    }};

    /**
     * Executes an integer expression based on the provided tokens.
     *
     * @param tokens The integer expression split into tokens.
     * @return The result of the evaluated expression.
     * @throws CompilationError If the expression is invalid.
     */
    public static int executeExpressionInteger(String[] tokens) throws CompilationError {
        Stack<Integer> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        // Define operator precedence
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("%", 2);

        int i = 0;
        while (i < tokens.length) {
            String token = tokens[i];

            if (token.equals("(")) {
                operatorStack.push(token);
                i++;
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    applyTopOperator(operandStack, operatorStack);
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                    operatorStack.pop(); // Remove "("
                } else {
                    throw new CompilationError("Mismatched parentheses");
                }
                i++;
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek())
                        && precedence.get(operatorStack.peek()) >= precedence.get(token)) {
                    applyTopOperator(operandStack, operatorStack);
                }
                operatorStack.push(token);
                i++;
            } else {
                // Operand: can be an integer or a variable
                int value = Variables.getIntegerValue(token);
                operandStack.push(value);
                i++;
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
     * @param operandStack  The stack containing integer operands.
     * @param operatorStack The stack containing operators.
     * @throws CompilationError If the operator is unsupported or there are insufficient operands.
     */
    private static void applyTopOperator(Stack<Integer> operandStack, Stack<String> operatorStack) throws CompilationError {
        if (operandStack.size() < 2) {
            throw new CompilationError("Insufficient operands");
        }
        String operator = operatorStack.pop();
        int right = operandStack.pop();
        int left = operandStack.pop();

        BiFunction<Integer, Integer, Integer> operation = arithmeticOperators.get(operator);
        if (operation == null) {
            throw new CompilationError("Unsupported operator: " + operator);
        }
        try {
            int result = operation.apply(left, right);
            operandStack.push(result);
        } catch (ArithmeticException e) {
            throw e;
        }
    }

    /**
     * Checks if a token is an arithmetic operator.
     *
     * @param token The token to check.
     * @return True if it's an operator, false otherwise.
     */
    private static boolean isOperator(String token) {
        return ExpressionInteger.arithmeticOperators.containsKey(token);
    }
}
