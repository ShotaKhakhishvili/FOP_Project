import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.function.Predicate;

class RunningState{;
    public static int pc = 0; // current line

    String[] args; // current instruction
    Instruction type; // current instruction type
    Assignment assType; // current assignment type


}

public class Runner extends RunningState{
    static Variables<Integer> ints = new Variables<>();

    String[] lines; // instructions line as strings
    String[][] lineArgs; // normalized instructions

    Runner(String fileName) throws FileNotFoundException {
        Parser parser = new Parser(fileName);

        parser.readFile();

        lines = parser.getLines();
        lineArgs = new String[lines.length][];

        for(int i = 0; i < lines.length; i++){
            lineArgs[i] = InstructionHandler.normalizeInstruction(lines[i]);
        }
    }

    public void run(){
        while(pc < lines.length){
            job();
            pc++;
        }
    }

    private void job(){
        args = lineArgs[pc];
        type = InstructionHandler.decode(args);

        if(type == Instruction.invalid)
            throw new RuntimeException("Instruction on line " + (pc + 1) + " is not valid");

        if(type == Instruction.assignment){
            assType = InstructionHandler.decodeAssignment(args);
            if(assType == Assignment.invalid)
                throw new RuntimeException("Assignment instruction on line " + (pc + 1) + " is not valid");

            AssignmentHandler.executeAssignment(args, assType);
        }
        else if(type == Instruction.declaration){
            DeclarationHandler.executeDeclaration(args);
        }
        else if(type == Instruction.print){
            PrintHandler.executePrint(args);
        }
    }
}
