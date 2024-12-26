import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Compiler {
    private static Set<Integer> ifs = new HashSet<>();
    private static Set<Integer> endifs = new HashSet<>();
    private static Set<Integer> wends = new HashSet<>();
    private static Set<Integer> whiles = new HashSet<>();
    public static Queue<String> outputQueue = new ArrayDeque<>();

    public static void compile() throws CompilationError {
        String[] args = Runner.lineArgs[RunningState.pc];
        Instruction instruction = InstructionHandler.decode(args);
        switch (instruction){
            case declaration:
                checkDeclaration(args);
                break;
            case assignment :
                checkAssignment(args);
                break;
            case iif:
                ifs.add(Runner.pc);
                checkIfStatement(args);
                break;
            case endif:
                endifs.add(Runner.pc);
                checkIfStatementEnd(args);
                break;
            case wwhile:
                whiles.add(Runner.pc);
                checkWhile(args);
                break;
            case wend:
                wends.add(Runner.pc);
                checkWhileEnd(args);
                break;
            case print:
                checkPrint(args);
                break;
            case invalid:
                throw new CompilationError("Invalid Statement");
        }
    }

    private static void checkDeclaration(String[] args) throws CompilationError{
        if(args.length != 4 || !args[2].equals("as") || !(args[3].equals("integer") || args[3].equals("boolean")))
            throw new CompilationError("Invalid Declaration on line ");

        if(Runner.intStack.peek().containsElement(args[1]))
            throw new CompilationError("Duplicate Variable Declaration On Line: ");

        if(!Variables.checkValidity(args[1]))
            throw new CompilationError("Illegal Variable Name On Line: ");

        try {
            DeclarationHandler.executeDeclaration(args);
        }catch (RuntimeException e){
            throw e;
        }
    }
    private static void checkAssignment(String[] args) throws CompilationError {
        if(!Runner.intStack.peek().containsElement(args[0]))
            throw new CompilationError("Undeclared Variable Usage On Line: ");

        if(!args[1].equals("="))
            throw new CompilationError("Invalid Instruction On Line: ");

        try {
            AssignmentHandler.executeAssignment(args);
        }catch (RuntimeException | CompilationError e){
            throw e;
        }
    }
    private static void checkIfStatement(String[] args) throws CompilationError{
        if(!args[0].equals("if") || !args[args.length - 1].equals("then"))
            throw new CompilationError("Invalid If Statement");

        try {
            IfStatementHandler.executeIfStatement(args);
        }catch (RuntimeException | CompilationError e){
            throw e;
        }
    }
    private static void checkIfStatementEnd(String[] args) throws CompilationError{
        if(ifs.size() < endifs.size())
            throw new CompilationError("Extra End If Statement");

        if(!(args[0].equals("end") && args[1].equals("if") && args.length == 2))
            throw new CompilationError("Invalid End If Statement");
    }
    private static void checkWhile(String[] args) throws CompilationError{
        try{
            LoopHandler.executeWhileLoop(args);
        }catch (RuntimeException | CompilationError e){
            throw e;
        }
    }
    private static void checkWhileEnd(String[] args) throws CompilationError{
        if(whiles.size() < wends.size())
            throw new CompilationError("Extra Wend Statement");
        if(!(args[0].equals("wend") && args.length == 1))
            throw new CompilationError("Invalid Wend Statement");
        try {
            LoopHandler.executeWendStatement();
        }catch (RuntimeException | CompilationError e){
            throw e;
        }catch (Exception e){
            throw new CompilationError(e.getMessage());
        }
    }
    private static void checkPrint(String[] args) throws CompilationError {
        try {
            String output = String.valueOf(PrintHandler.getPrintMessage(args));
            if(Runner.intStack.size() == 1)
                outputQueue.add("PROGRAM OUTPUT: " + output);
        }catch (RuntimeException | CompilationError e){
            throw e;
        }
    }
}
