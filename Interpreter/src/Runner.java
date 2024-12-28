import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Runner{
    public static Integer pc; // current line
    String[] args; // current instruction
    Instruction type; // current instruction type
    public static boolean testing = true;

    static List<Triple<Integer, String[], List<String>>> scopeList = new ArrayList<>();
    static List<Boolean> isLoop = new ArrayList<>();

    // Stack to hold Variables of Boolean type, managing the state of boolean variables in a Last In, First Out (LIFO) order.
        static Stack<Variables<Boolean>> boolStack = new Stack<>();

    // Stack to hold Variables of Integer type, managing the state of integer variables in a Last In, First Out (LIFO) order.
        static Stack<Variables<Integer>> intStack = new Stack<>();

    // Array to hold the lines of code or instructions for the program. Each element represents a line.
        static String[] lines;

    // 2D array to hold the arguments for each line of code or instruction. Each row corresponds to a line and holds its associated arguments.
        static String[][] lineArgs;

    /**
     * Constructor for the Runner class that initializes the necessary components
     * for parsing and processing a given source file.
     *
     * @param fileName The name of the source file to be parsed.
     * @throws CompilationError If there is an error during compilation or parsing of the file.
     */
    public static void Declare(String fileName) throws CompilationError, URISyntaxException {
        pc = 0;
        scopeList.clear();
        isLoop.clear();
        boolStack.clear();
        intStack.clear();



        // Create a new Parser instance to read and process the given file.
        Parser parser = new Parser(fileName);

        // Initialize the boolean and integer stacks with empty Variables objects.
        // These stacks are used to manage the state of boolean and integer variables.
        boolStack.add(new Variables<>());
        intStack.add(new Variables<>());

        // Read the contents of the file using the parser.
        parser.readFile();

        // Retrieve the lines of code from the parsed file.
        lines = parser.getLines();

        // Initialize the lineArgs array to hold arguments for each line in the file.
        // The size of the array matches the number of lines in the file.
        lineArgs = new String[lines.length][];

        // Loop through each line, split the line into arguments, and store them in the lineArgs array.
        for(int i = 0; i < lines.length; i++){
            // Convert the line to lowercase and split it into arguments using InstructionHandler.
            lineArgs[i] = InstructionHandler.split(lines[i].toLowerCase());
        }
    }

    /**
     * Executes the compilation process by iterating through each line of code
     * and invoking the compiler for each line.
     * The method continuously compiles the code while the program counter (pc)
     * is less than the total number of lines.
     *
     * @throws CompilationError If an error occurs during the compilation process.
     */
    public static void run() throws CompilationError {
        // Continue compiling each line of code until all lines have been processed
        while(pc < lines.length){
            Compiler.compile();
            pc++;
        }
    }

    /**
     * Retrieves the 2D array of arguments for each line of code or instruction.
     * This method returns the `lineArgs` array, which holds the arguments
     * for each line in the source file, where each row corresponds to a
     * line of code and each column holds its respective argument.
     *
     * @return A 2D array of strings representing the arguments for each line of code.
     */
    public static String[][] getLineArgs(){
        return lineArgs;
    }


    /**
     * Executes the appropriate handler based on the decoded instruction type.
     * The method first decodes the instruction from the current line, then checks
     * its validity. Based on the instruction type, it calls the relevant handler
     * to execute the corresponding operation.
     *
     * @throws Exception If any error occurs during the execution of an instruction.
     */
    private void job() throws Exception {

        // Retrieve the arguments for the current line of code (instruction)
        args = lineArgs[pc];

        // Decode the instruction to determine its type
        type = InstructionHandler.decode(args);

        // Check if the decoded instruction is invalid and throw an exception if so
        if(type == Instruction.invalid)
            throw new RuntimeException("Instruction on line " + (pc + 1) + " is not valid");


        // Use a switch statement to handle the instruction based on its type
        switch (type) {
            // Handle assignment instructions
            case assignment:
                AssignmentHandler.executeAssignment(args);
                break;

            // Handle declaration instructions
            case declaration:
                DeclarationHandler.executeDeclaration(args);
                break;

            // Handle print instructions
            case print:
                PrintHandler.executePrint(args,false);
                break;

            // Handle 'if' statement instructions
            case iif:
                IfStatementHandler.executeIfStatement(args);
                break;

            // Handle while loop instructions
            case wwhile:
                LoopHandler.executeWhileLoop(args);
                break;

            // Handle 'wend' (end of loop) instructions
            case wend:
                LoopHandler.executeWendStatement();
                break;
        }
    }

    /**
     * Removes the most recent Variables objects from both the boolean and integer stacks.
     * This method effectively discards the topmost state of the boolean and integer variables,
     * which were saved temporarily in the `saveVariableToTemp` method.
     */

    public static void loadVariableToTemp(){
        // Remove the most recent Variables object from the boolean stack.
        boolStack.pop();

        // Remove the most recent Variables object from the integer stack.
        intStack.pop();
    }

    /**
     * Saves the current state of boolean and integer variables to temporary stacks.
     * This method creates new `Variables` objects for both booleans and integers,
     * copies the current maps of variables from the top of the respective stacks,
     * and then pushes the new `Variables` objects onto the stacks.
     */
    public static void saveVariableToTemp(){

        // Create new Variables objects for booleans and integers to hold temporary data
        Variables<Boolean> newBool = new Variables<>();
        Variables<Integer> newInteger = new Variables<>();

        // Copy the current boolean variables from the top of the boolean stack into the new object
        newBool.getMap().putAll(boolStack.peek().getMap());

        // Copy the current integer variables from the top of the integer stack into the new object
        newInteger.getMap().putAll(intStack.peek().getMap());

        // Push the newly created Variables objects onto their respective stacks
        boolStack.add(newBool);
        intStack.add(newInteger);
    }


}
