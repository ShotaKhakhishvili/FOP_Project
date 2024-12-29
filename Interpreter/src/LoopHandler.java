import java.security.KeyPair;
import java.util.*;


public class LoopHandler {



    /**
     * Executes a while loop by evaluating the boolean condition and managing the program counter (pc)
     * to repeat or exit the loop based on the condition.
     * @param args - The arguments for the while loop, with the first argument being the "while" keyword.
     * @throws CompilationError if there's an error during compilation or execution.
     */
    public static void executeWhileLoop(String[] args) throws CompilationError {

        // Extract the condition/expression arguments from the input.
        String[] expressionArgs = new String[args.length - 1];

        //Skip the first item in args and copy the rest into a new array.
        for (int i = 1; i < args.length; i++){
            expressionArgs[i-1] = args[i];
        }

        // Add a new loop scope to the scope list and mark it as a loop.
        Runner.scopeList.add(new Triple<>(Runner.pc, expressionArgs, new ArrayList<String>()));
        Runner.isLoop.add(true);

        // Check if the loop condition is false initially. skip the loop body if true.
        if(!ExpressionBoolean.executeExpression(expressionArgs)){

            int whileCounter = 1; // Tracks nested while-wend blocks.
            String[][] lineArgs = Runner.getLineArgs(); // Retrieve program arguments for each line.
            Runner.pc++; // Move to the next line.
            Runner.saveVariableToTemp(); // Save current variable state to a temporary location.


            //================================================================================================
            // Loop through the program until the matching "wend" is found or nested while blocks are handled.
            while(whileCounter > 0){
                try{
                    if(Runner.testing)
                        Compiler.compile(); // Compile in test mode if applicable.
                } catch (RuntimeException e){
                    Runner.pc++; // Ignore runtime errors and proceed to the next instruction.
                    continue;
                }

                Instruction currentInstruction = InstructionHandler.decode(lineArgs[Runner.pc]);
                if(currentInstruction == Instruction.wend) {
                    whileCounter--; // Decrement counter if "wend" is encountered.
                } else if(currentInstruction == Instruction.wwhile) {
                    whileCounter++; // Increment counter if nested "while" is encountered.
                }
                Runner.pc++; // Move to the next line.
            }
            //================================================================================================

            Runner.pc--; // Adjust program counter to correct position.
            executeWendStatement(); // Finalize the "wend" statement
            Runner.loadVariableToTemp(); // Restore variable state from the temporary location.
        }
    }


    
    /**
     * Executes the "wend" statement by managing variable scopes and determining
     * whether to re-evaluate the loop condition or exit the loop.
     * @throws CompilationError if there's an error during the execution of the statement.
     */
    public static void executeWendStatement() throws CompilationError {

        // Find the most recent loop scope in the scope list.
        int index = Runner.scopeList.size() - 1;
        while(!Runner.isLoop.get(index))
            index--;


        // Get the details of the current loop's scope (start position, condition, and temporary variables).
        Triple<Integer,String[],List<String>> current = Runner.scopeList.get(index);


        // Clear temporary variables from the current loop's scope.
        while(!current.getThird().isEmpty()) {
            if (Runner.intStack.peek().containsElement(current.getThird().get(0)))
                Runner.intStack.peek().deleteVariable(current.getThird().remove(0));

             else if (Runner.boolStack.peek().containsElement(current.getThird().get(0)))
                Runner.boolStack.peek().deleteVariable(current.getThird().remove(0));
        }


        // If the condition evaluates to true, reset the program counter to loop start.
        // Otherwise, remove the loop from the scope list and exit the loop.
        if(ExpressionBoolean.executeExpression(current.getSecond()) && Runner.boolStack.size() == 1 && !Runner.testing){
            Runner.pc = current.getFirst();
        } else {
            Runner.scopeList.remove(index);
            Runner.isLoop.remove(index);
        }

    }
}
