import java.security.KeyPair;
import java.util.*;

public class LoopHandler {

    public static void executeWhileLoop(String[] args) throws CompilationError {
        String[] expressionArgs = new String[args.length - 1];

        for(int i = 1; i < args.length; i++){
            expressionArgs[i-1] = args[i];
        }

        Runner.scopeList.add(new Triple<>(Runner.pc, expressionArgs, new ArrayList<String>()));
        Runner.isLoop.add(true);

        if(!ExpressionBoolean.executeExpression(expressionArgs)){
            int whileCounter = 1;
            String[][] lineArgs = Runner.getLineArgs();
            Runner.pc++;

            Runner.saveVariableToTemp();

            while(whileCounter > 0){
                try{
                    Compiler.compile();
                }catch (RuntimeException e){
                    Runner.pc++;
                    continue;
                }
                Instruction currentInstruction = InstructionHandler.decode(lineArgs[Runner.pc]);
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
        int index = Runner.scopeList.size() - 1;

        while(!Runner.isLoop.get(index))
            index--;

        Triple<Integer,String[],List<String>> current = Runner.scopeList.get(index);

        while(!current.getThird().isEmpty()){
            Runner.intStack.peek().deleteVariable(current.getThird().get(0));
            try{
                Runner.boolStack.peek().deleteVariable(current.getThird().remove(0));
            }catch (IndexOutOfBoundsException e){

            }
        }

        if(ExpressionBoolean.executeExpression(current.getSecond()) && Runner.boolStack.size() == 1){
            Runner.pc = current.getFirst();
        }else{
            Runner.scopeList.remove(index);
            Runner.isLoop.remove(index);
        }
    }
}
