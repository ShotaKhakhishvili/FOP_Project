import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class Variables<T>{

    private Map<String,T> map = new HashMap<>();

    /**
     * Deletes a declared variable from the current context.
     *
     * @param varName the name of the variable to delete
     * This method performs the following operations:
     * 1. Checks if the variable name (`varName`) exists in the `map`:
     *    - If the variable is not found, a `RuntimeException` is thrown with an explanatory message.
     * 2. Retrieves the top element of the `LoopHandler.stack` (assumed to represent the current loop context)
     *    and removes the variable name from the third component of that element, maintaining scope consistency.
     * 3. Removes the variable from the `map` to ensure it is no longer accessible globally or in any scope.
     * Note: This method assumes that `LoopHandler.stack` is never empty when this method is called and
     *       that each element in the stack has a `getThird()` method returning a collection of variable names.
     *       If these assumptions can be violated, additional null checks or empty stack validations may be required.
     *
     * @throws RuntimeException if the variable `varName` is not declared in the `map`.
     */
    public void deleteVariable(String varName){
        try {
            map.remove(varName);
            Runner.scopeList.get(Runner.scopeList.size() - 1).getThird().remove(varName);
        }catch (Exception e){
        }
    }

    /**
     * Validates the syntax of a variable name based on predefined rules.
     *
     * @param varName the variable name to validate
     * @return {@code true} if the variable name is valid; {@code false} otherwise
     * This method performs the following checks:
     * 1. Ensures the first character of `varName` is not a digit:
     *    - If the first character is a digit (0-9), the method returns {@code false}.
     * 2. Iterates through each character in `varName`:
     *    - If any character is found in the `InstructionHandler.invalidChars` collection,
     *      the method returns {@code false}.
     * 3. Returns {@code true} if the variable name passes all validation checks.
     * Notes:
     * - The `InstructionHandler.invalidChars` collection is assumed to contain characters that are
     *   not permitted in variable names (e.g., special characters, spaces, etc.).
     * - This method does not validate null or empty strings. Consider adding null/empty string checks
     *   if needed to handle edge cases explicitly.
     * Example:
     * - Input: "var1" → {@code true}
     * - Input: "1var" → {@code false} (starts with a digit)
     * - Input: "var@name" → {@code false} (contains an invalid character '@')
     */
    public static boolean checkValidity(String varName){
        if(varName.charAt(0) <= '9' && varName.charAt(0) >= '0') return false;

        for(char ch : varName.toCharArray()){
            if(InstructionHandler.invalidChars.contains(ch))return false;
        }

        return true;
    }

    /**
     * Retrieves the value of a declared variable.
     *
     * @param varName the name of the variable to retrieve
     * @return the value associated with the specified variable
     * @throws CompilationError if the variable is not declared in the `map`
     * @throws RuntimeException if the variable name or its value is `null`
     * This method performs the following operations:
     * 1. Validates the input parameter (`varName`):
     *    - Throws a `RuntimeException` if `varName` is `null` with an appropriate error message.
     * 2. Checks if the variable is declared in the `map`:
     *    - If declared, retrieves its value.
     *    - If the value is `null`, throws a `RuntimeException` with an explanatory message.
     * 3. If the variable is not declared, throws a `CompilationError` with a message indicating that the variable
     *    has not been declared.
     * Notes:
     * - The `map` is assumed to be a key-value store that maps variable names to their values.
     * - This method enforces strict error handling for undeclared variables and null values, ensuring robust behavior.
     * - Consider using a more descriptive exception for null value errors if `RuntimeException` is too generic in this context.
     */
    public T getValue(String varName) throws CompilationError {
        if(varName == null)
            throw new RuntimeException("Variable '" + varName + "' is null");
        if(map.containsKey(varName)) {
            if(map.get(varName) == null)
                throw new RuntimeException("Variable '" + varName + "' is null");
            return map.get(varName);
        }
        else
            throw new CompilationError("Variable '" + varName + "' is not declared");
    }


    /**
     * Updates the value of an existing variable in the current context.
     *
     * @param varName the name of the variable to update
     * @param varValue the new value to assign to the variable
     * This method performs the following operations:
     * 1. Checks if the variable name (`varName`) exists in the `map`:
     *    - If the variable is found, its value is updated to `varValue` in the `map`.
     *    - If the variable is not found, an error message is printed to the standard output.
     * Note:
     * - This method does not throw an exception for undeclared variables; instead, it prints an error message.
     *   If stricter error handling is desired, consider replacing the `System.out.println` statement with
     *   an exception (e.g., `throw new RuntimeException`).
     * - The `map` data structure is assumed to be a key-value store that maintains variable names and their values.
     */
    public void setValue(String varName, T varValue) throws CompilationError {
        if(map.containsKey(varName)) {
            map.put(varName, varValue);
        }
        else {
            throw new CompilationError("Variable '" + varName + "' is not declared");
        }
    }

    /**
     * Checks if a variable is declared in the current context.
     * @param varName the name of the variable to check
     * @return {@code true} if the variable is declared in the `map`; {@code false} otherwise
     * This method performs the following operation:
     * - Queries the `map` to determine if it contains the specified variable name (`varName`) as a key.
     * Notes:
     * - This method assumes that `map` is a key-value store representing the declared variables and their values.
     * - It provides a lightweight way to check variable declaration without accessing or modifying the map's contents.
     */
    public boolean containsElement(String varName){
        return map.containsKey(varName);
    }


    /**
     * Declares a new variable in the current context and associates it with a specified value.
     *
     * @param varName the name of the variable to declare
     * @param varValue the value to assign to the declared variable
     * This method performs the following operations:
     * 1. Checks if the `LoopHandler.stack` is not empty. If the stack contains elements:
     *    - Retrieves the top element (assumed to represent the current context in a loop structure).
     *    - Adds the variable name (`varName`) to the third component of the top element, presumably
     *      for tracking variables specific to the current loop context.
     * 2. Updates the `map` data structure to store the variable name and its corresponding value.
     *    - Ensures that the variable can be accessed globally or within the relevant scope.
     * Note: It assumes that `LoopHandler.stack` is a stack data structure where each element is a
     *       custom object providing a `getThird()` method, and `map` is a predefined key-value store.
     */
    public void declareVariable(String varName, T varValue){
        if(!Runner.scopeList.isEmpty()){
            Runner.scopeList.get(Runner.scopeList.size() - 1).getThird().add(varName);
        }

        map.put(varName, varValue);
    }

    /**
     * Retrieves the entire mapping of variable names to their values.
     *
     * @return a {@code Map<String, T>} representing the current variable declarations and their associated values
     * This method provides direct access to the underlying `map` data structure, which stores
     * the declared variables and their corresponding values.
     * Notes:
     * - Modifications to the returned map will directly affect the internal state of the object.
     *   If encapsulation is a concern, consider returning an unmodifiable view or a copy of the map instead.
     * - The generic type {@code T} represents the type of values associated with the variable names.
     */
    public Map<String, T> getMap() {
        return map;
    }


    /**
     * Retrieves the integer value of a token, either directly or from Runner.ints.
     *
     * @param token The token representing an integer or variable.
     * @return The integer value.
     */
    public static int getIntegerValue(String token) throws CompilationError {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            // Assume it's a variable
            Integer value;
            try {
                value = Runner.intStack.peek().getValue(token);
            }catch (CompilationError ev){
                value = Runner.boolStack.peek().getValue(token) ? 1 : 0;
            }
            if (value == null) {
                throw new CompilationError("Undefined integer variable: " + token);
            }
            return value;
        }
    }
}
