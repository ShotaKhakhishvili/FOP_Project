import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class InputHandler {
    public static void executeInput(String[] args) throws CompilationError {
        if(Runner.testing) return;

        Scanner scanner = new Scanner(System.in);

        System.out.print("PROGRAM INPUT : ");

        String name = scanner.nextLine();        // Read a line of input

        try {
            if(Runner.intStack.peek().containsElement(args[1]))
                Runner.intStack.peek().setValue(args[1], Integer.valueOf(name));
            else
                Runner.boolStack.peek().setValue(args[1], Boolean.valueOf(name));
        }catch (NumberFormatException e){
            throw new RuntimeException("Invalid Input Formating. Input Only Supports Numbers :(");
        }

        scanner.close();
    }

}
