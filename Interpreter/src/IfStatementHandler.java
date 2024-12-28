import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Handles IF statements in the BASIC interpreter.
 */
    public class IfStatementHandler {
        /**
     * Executes IF statement.
     * Evaluates the condition and skips the block of code if the condition is false.
     *
     * @param args the arguments of the IF statement, including the condition and instructions.
     * @throws CompilationError if there is an error during execution.
     */
    public static void executeIfStatement(String[] args) throws CompilationError {

        // Extract the condition part of the IF statement from the arguments
        String[] expressionArgs = new String[args.length - 2];

        // Copy the condition arguments, skipping the IF keyword and the END keyword
        for (int i = 1; i < args.length - 1; i++){
            expressionArgs[i-1] = args[i];
        }

        // LoopHandler.stack.add(new Triple<>(Runner.pc, expressionArgs, new ArrayList<String>()));

        // Check the condition of the IF statement
        if (!ExpressionBoolean.executeExpression(expressionArgs)) {
            int ifCounter = 1; // Tracks nested IF statements
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++; // Move to the next instruction;


            // Temporarily save variables for later use
            Runner.saveVariableToTemp();



            while(ifCounter > 0){
                Compiler.compile();
                Instruction currentInstruction = InstructionHandler.decode(lineArgs[RunningState.pc]);
                if(currentInstruction == Instruction.endif)
                    ifCounter--;
                else if(currentInstruction == Instruction.iif)
                    ifCounter++;
                Runner.pc++;
            }

            Runner.loadVariableToTemp();

            Runner.pc--;
        }
    }

    public static void executeEndIf(){
        Triple<Integer,String[], List<String>> current = LoopHandler.stack.pop();

        while(!current.getThird().isEmpty()){
            Runner.intStack.peek().deleteVariable(current.getThird().get(0));
        }
    }
}
