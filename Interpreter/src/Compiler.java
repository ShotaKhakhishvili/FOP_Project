import java.util.Queue;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.HashSet;

public class Compiler {

    // Stores positions of different instruction types
    private static Set<Integer> ifs = new HashSet<>(); // Tracks positions of "if" statements
    private static Set<Integer> endifs = new HashSet<>(); // Tracks positions of "end if" statements
    private static Set<Integer> wends = new HashSet<>(); // Tracks positions of "wend" statements
    private static Set<Integer> whiles = new HashSet<>(); // Tracks positions of "while" statements

    // Queue to collect output messages
    public static Queue<String> outputQueue = new ArrayDeque<>();

    // Main method for compiling a single line of code
    public static void compile() throws CompilationError {
        // Retrieve the current line's arguments
        String[] args = Runner.lineArgs[RunningState.pc];
        // Decode the instruction from the arguments
        Instruction instruction = InstructionHandler.decode(args);

        // Handle the instruction based on its type
        switch (instruction) {
            case wend:
                wends.add(Runner.pc); // Record "wend" position
                validateWend(args); // Validate the "wend" statement
                break;
            case print:
                validatePrint(args); // Validate the "print" statement
                break;
            case invalid:
                throw new CompilationError("Invalid Statement"); // Throw error for invalid statements
            case declaration:
                validateDeclaration(args); // Validate the "declaration" statement
                break;
            case assignment:
                validateAssignment(args); // Validate the "assignment" statement
                break;
            case iif:
                ifs.add(Runner.pc); // Record "if" position
                validateIfStatement(args); // Validate the "if" statement
                break;
            case endif:
                endifs.add(Runner.pc); // Record "end if" position
                validateEndIf(args); // Validate the "end if" statement
                break;
            case wwhile:
                whiles.add(Runner.pc); // Record "while" position
                validateWhile(args); // Validate the "while" statement
                break;
        }
    }

    // Ensures a declaration statement is valid
    private static void validateDeclaration(String[] args) throws CompilationError {
        // Check if the declaration follows the correct format
        if (args.length != 4 || !args[2].equals("as") || !(args[3].equals("integer") || args[3].equals("boolean"))) {
            throw new CompilationError("Invalid Declaration on line");
        }

        // Check if the variable is already declared in the current scope
        if (Runner.intStack.peek().containsElement(args[1])) {
            throw new CompilationError("Duplicate Variable Declaration On Line:");
        }

        // Ensure the variable name is valid
        if (!Variables.checkValidity(args[1])) {
            throw new CompilationError("Illegal Variable Name On Line:");
        }

        // Execute the declaration using the handler
        try {
            DeclarationHandler.executeDeclaration(args);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    // Ensures an assignment statement is valid
    private static void validateAssignment(String[] args) throws CompilationError {
        // Check if the variable being assigned is declared
        if (!Runner.intStack.peek().containsElement(args[0])) {
            throw new CompilationError("Undeclared Variable Usage On Line:");
        }

        // Ensure the assignment uses the correct "=" operator
        if (!args[1].equals("=")) {
            throw new CompilationError("Invalid Instruction On Line:");
        }

        // Execute the assignment using the handler
        try {
            AssignmentHandler.executeAssignment(args);
        } catch (RuntimeException | CompilationError e) {
            throw e;
        }
    }

    // Ensures an if statement is valid
    private static void validateIfStatement(String[] args) throws CompilationError {
        // Check if the "if" statement starts with "if" and ends with "then"
        if (!args[0].equals("if") || !args[args.length - 1].equals("then")) {
            throw new CompilationError("Invalid If Statement");
        }

        // Execute the "if" statement using the handler
        try {
            IfStatementHandler.executeIfStatement(args);
        } catch (RuntimeException | CompilationError e) {
            throw e;
        }
    }

    // Ensures an end-if statement is valid
    private static void validateEndIf(String[] args) throws CompilationError {
        // Ensure there are matching "if" statements for "end if"
        if (ifs.size() < endifs.size()) {
            throw new CompilationError("Extra End If Statement");
        }

        // Validate the syntax of the "end if" statement
        if (!(args[0].equals("end") && args[1].equals("if") && args.length == 2)) {
            throw new CompilationError("Invalid End If Statement");
        }
    }

    // Ensures a while statement is valid
    private static void validateWhile(String[] args) throws CompilationError {
        // Execute the "while" statement using the handler
        try {
            LoopHandler.executeWhileLoop(args);
        } catch (RuntimeException | CompilationError e) {
            throw e;
        }
    }

    // Ensures a wend statement is valid
    private static void validateWend(String[] args) throws CompilationError {
        // Ensure there are matching "while" statements for "wend"
        if (whiles.size() < wends.size()) {
            throw new CompilationError("Extra Wend Statement");
        }

        // Validate the syntax of the "wend" statement
        if (!(args[0].equals("wend") && args.length == 1)) {
            throw new CompilationError("Invalid Wend Statement");
        }

        // Execute the "wend" statement using the handler
        try {
            LoopHandler.executeWendStatement();
        } catch (RuntimeException | CompilationError e) {
            throw e;
        } catch (Exception e) {
            throw new CompilationError(e.getMessage());
        }
    }

    // Ensures a print statement is valid
    private static void validatePrint(String[] args) throws CompilationError {
        // Retrieve the output message and add it to the queue
        try {
            String output = String.valueOf(PrintHandler.getPrintMessage(args));
            if (Runner.intStack.size() == 1) {
                outputQueue.add("PROGRAM OUTPUT: " + output);
            }
        } catch (RuntimeException | CompilationError e) {
            throw e;
        }
    }
}
