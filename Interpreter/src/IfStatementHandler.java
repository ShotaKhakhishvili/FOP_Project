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
    static Map<String, BiPredicate<Boolean,Boolean>> mapB = new HashMap<>(){{
        put("and", (o1,o2) -> o1 == o2);
        put("or", (o1,o2) -> o1 || o2);
    }};

    public static void executeIfStatement(String[] args) throws Exception {
        if(args[0] != "if" || args[args.length-1] != "then")
            invalidIfStatement();

        if(!(Runner.ints.containsElement(args[1]) || AssignmentHandler.isNumber(args[1])))
            throw new RuntimeException("If statement was invalid. '" + args[1] + "' was the problem on line " + (Runner.pc + 1));

        if(!(Runner.ints.containsElement(args[3]) || AssignmentHandler.isNumber(args[3])))
            throw new RuntimeException("If statement was invalid. '" + args[3] + "' was the problem on line " + (Runner.pc + 1));

        int first; // left operand
        if(AssignmentHandler.isNumber(args[1]))
            first = Integer.parseInt(args[1]);
        else
            first = Runner.ints.getValue(args[1]);

        int second;
        if(AssignmentHandler.isNumber(args[3]))
            second = Integer.parseInt(args[3]);
        else
            second = Runner.ints.getValue(args[3]);

        if(ExpressionBoolean.executeExpression(args)){
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
