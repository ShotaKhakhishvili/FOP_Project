import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Predicate;

class RunningState{;
    public static Integer pc = 0; // current line
    public static int active = 0;
    String[] args; // current instruction
    Instruction type; // current instruction type
    Assignment assType; // current assignment type


}

public class Runner extends RunningState{

    static Stack<Variables<Boolean>> boolStack = new Stack<>();
    static Stack<Variables<Integer>> intStack = new Stack<>();

    static String[] lines; // instructions line as strings
    static String[][] lineArgs; // normalized instructions

    Runner(String fileName) throws CompilationError {
        Parser parser = new Parser(fileName);
        boolStack.add(new Variables<>());
        intStack.add(new Variables<>());

        parser.readFile();

        lines = parser.getLines();
        lineArgs = new String[lines.length][];

        for(int i = 0; i < lines.length; i++){
            lineArgs[i] = InstructionHandler.split(lines[i].toLowerCase());
        }
    }

    public static void saveVariableToTemp(){
        Variables<Boolean> newBool = new Variables<>();
        Variables<Integer> newInteger = new Variables<>();

        newBool.getMap().putAll(boolStack.peek().getMap());
        newInteger.getMap().putAll(intStack.peek().getMap());

        boolStack.add(newBool);
        intStack.add(newInteger);
    }

    public static void loadVariableToTemp(){
        boolStack.pop();
        intStack.pop();
    }

    public static String[][] getLineArgs(){
        return lineArgs;
    }

    public void run() throws CompilationError {
        while(pc < lines.length){
            Compiler.compile();
            pc++;
        }
    }

    private void job() throws Exception {
        args = lineArgs[pc];
        type = InstructionHandler.decode(args);

        if(type == Instruction.invalid)
            throw new RuntimeException("Instruction on line " + (pc + 1) + " is not valid");

        if(type == Instruction.assignment){
            AssignmentHandler.executeAssignment(args);
        }
        else if(type == Instruction.declaration){
            DeclarationHandler.executeDeclaration(args);
        }
        else if(type == Instruction.print){
            PrintHandler.executePrint(args);
        }
        else if(type == Instruction.iif){
            IfStatementHandler.executeIfStatement(args);
        }else if(type == Instruction.wwhile){
            LoopHandler.executeWhileLoop(args);
        }else if(type == Instruction.wend){
            LoopHandler.executeWendStatement();
        }
    }
}
