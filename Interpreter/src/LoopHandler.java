import java.security.KeyPair;
import java.util.*;

public class LoopHandler {
    static Stack<Triple<Integer, String[], List<String>>> stack = new Stack<>();

    public static void executeWhileLoop(String[] args) throws CompilationError {
        String[] expressionArgs = new String[args.length - 1];

        for(int i = 1; i < args.length; i++){
            expressionArgs[i-1] = args[i];
        }

        stack.add(new Triple<>(Runner.pc, expressionArgs, new ArrayList<String>()));

        if(!ExpressionBoolean.executeExpression(expressionArgs)){
            int whileCounter = 1;
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++;

            Runner.saveVariableToTemp();

            while(whileCounter > 0){
                Compiler.compile();
                Instruction currentInstruction = InstructionHandler.decode(lineArgs[RunningState.pc]);
                if(currentInstruction == Instruction.wend)
                    whileCounter--;
                else if(currentInstruction == Instruction.wwhile)
                    whileCounter++;
                Runner.pc++;
            }
            Runner.loadVariableToTemp();

            Runner.pc--;
        }
    }
    public static void executeWendStatement() throws CompilationError {
        Triple<Integer,String[],List<String>> current = stack.peek();

        while(!current.getThird().isEmpty()){
            Runner.intStack.peek().deleteVariable(current.getThird().get(0));
        }

        if(ExpressionBoolean.executeExpression(current.getSecond()) && Runner.intStack.size() == 1){
            Runner.pc = current.getFirst();
        }else{
            stack.pop();
        }
    }
}
