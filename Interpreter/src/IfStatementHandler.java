import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class IfStatementHandler {

    public static void executeIfStatement(String[] args) throws CompilationError {
        String[] expressionArgs = new String[args.length - 2];

        for(int i = 1; i < args.length - 1; i++){
            expressionArgs[i-1] = args[i];
        }

        Runner.scopeList.add(new Triple<>(Runner.pc, expressionArgs, new ArrayList<String>()));
        Runner.isLoop.add(false);

        if(!ExpressionBoolean.executeExpression(expressionArgs)){
            int ifCounter = 1;
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++;

            Runner.saveVariableToTemp();

            while(ifCounter > 0){
                Compiler.compile();
                Instruction currentInstruction = InstructionHandler.decode(lineArgs[Runner.pc]);
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
        int index = Runner.scopeList.size() - 1;

        while(Runner.isLoop.get(index))
            index--;

        Triple<Integer,String[],List<String>> current = Runner.scopeList.remove(index);

        Runner.isLoop.remove(index);

        while(!current.getThird().isEmpty()){
            Runner.intStack.peek().deleteVariable(current.getThird().get(0));
            try{
                Runner.boolStack.peek().deleteVariable(current.getThird().remove(0));
            }catch (IndexOutOfBoundsException e){

            }
        }
    }
}
