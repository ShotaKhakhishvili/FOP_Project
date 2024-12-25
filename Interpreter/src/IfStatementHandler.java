import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class IfStatementHandler {
    static Map<String, BiPredicate<Integer,Integer>> map = new HashMap<>(){{
        put("<", (o1,o2) -> o1 < o2);
        put(">", (o1,o2) -> o1 > o2);
        put("=", (o1,o2) -> o1 == o2);
        put("<>", (o1,o2) -> o1 != o2);
    }};

    public static void executeIfStatement(String[] args) throws Exception {
        if(!args[0].equals("if") || !args[args.length - 1].equals("then"))
            invalidIfStatement();

        String[] expressionArgs = new String[args.length - 2];

        for(int i = 1; i < args.length - 1; i++){
            expressionArgs[i-1] = args[i];
        }

        if(!ExpressionBoolean.executeExpression(expressionArgs)){
            int ifCounter = 1;
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++;
            while(ifCounter > 0){
                Instruction currentInstruction = InstructionHandler.decode(lineArgs[RunningState.pc]);
                if(currentInstruction == Instruction.endif)
                    ifCounter--;
                else if(currentInstruction == Instruction.iif)
                    ifCounter++;
                Runner.pc++;
            }
            Runner.pc--;
        }
    }

    private static void invalidIfStatement(){
        throw new RuntimeException("If statement on line " + (Runner.pc + 1) + " is not valid");
    }
}
