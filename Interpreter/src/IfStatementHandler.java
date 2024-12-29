import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


/**
 * Handles the execution of IF statements and their corresponding ENDIF statements.
 */
public class IfStatementHandler {


    /**
     * Executes an IF statement by evaluating its condition and handling conditional branching.
     *
     * @param args The arguments of the IF statement, including the condition and the body.
     * @throws CompilationError if a compilation error occurs during execution.
     */
    public static void executeIfStatement(String[] args) throws CompilationError {

        // Get the condition from the arguments.
        String[] expressionArgs = new String[args.length - 2];
        for (int i = 1; i < args.length - 1; i++) {
            expressionArgs[i - 1] = args[i];
        }

        // Add the current scope to the scope list for tracking.
        Runner.scopeList.add(new Triple<>(Runner.pc, expressionArgs, new ArrayList<String>()));
        Runner.isLoop.add(false);


        // Evaluate the IF condition. If false, skip the corresponding code block.
        if (!ExpressionBoolean.executeExpression(expressionArgs)) {

            int ifCounter = 1; // Tracks nested IF statements.
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++;

            // Save current variables to temporary storage.
            Runner.saveVariableToTemp();


            // Skip lines until the matching ENDIF is found.
            while (ifCounter > 0) {
                try {
                    if (Runner.testing) {
                        Compiler.compile(); // Compiles the next line if in testing mode.
                    }
                } catch (RuntimeException e) {
                    Runner.pc++; // Skip to the next line in case of an error.
                    continue;
                }

                // Decode the current instruction to determine control flow.
                Instruction currentInstruction = InstructionHandler.decode(lineArgs[Runner.pc]);
                if (currentInstruction == Instruction.endif) {
                    ifCounter--; // Found an ENDIF, decrease the counter.
                } else if (currentInstruction == Instruction.iif) {
                    ifCounter++; // Found a nested IF, increase the counter.
                }
                Runner.pc++;
            }

            // Adjust the program counter to point to the correct line.
            Runner.pc--;

            // Clean up after skipping the IF block.
            executeEndIf();

            // Restore variables from temporary storage.
            Runner.loadVariableToTemp();

        }
    }


    /**
     * Cleans up after an ENDIF by removing variables and scope from the current context.
     */
    public static void executeEndIf() {

        // Find the most recent scope that is not part of a loop.
        int index = Runner.scopeList.size() - 1;
        while (Runner.isLoop.get(index)) {
            index--;
        }

        // Remove the current scope and associated data.
        Triple<Integer, String[], List<String>> current = Runner.scopeList.remove(index);

        // Remove the corresponding loop flag for the identified scope.
        Runner.isLoop.remove(index);

        // Process all variables associated with the current scope.
        while(!current.getThird().isEmpty()){

            // If the variable exists in the integer stack, delete it from the stack.
            if (Runner.intStack.peek().containsElement(current.getThird().get(0)))
                Runner.intStack.peek().deleteVariable(current.getThird().remove(0));

            // If the variable exists in the boolean stack, delete it from the stack.
            else if (Runner.boolStack.peek().containsElement(current.getThird().get(0)))
                Runner.boolStack.peek().deleteVariable(current.getThird().remove(0));


        }
    }
}
