import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class IfStatementHandler {

    public static void executeIfStatement(String[] args) throws CompilationError {
        String[] expressionArgs = new String[args.length - 2];

        for(int i = 1; i < args.length - 1; i++){
            expressionArgs[i-1] = args[i];
        }

        if(!ExpressionBoolean.executeExpression(expressionArgs)){
            int ifCounter = 1;
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++;

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

    }
}
