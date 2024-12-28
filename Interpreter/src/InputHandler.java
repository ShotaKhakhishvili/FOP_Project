import java.util.*;

public class InputHandler {
    static Scanner scanner = new Scanner(System.in);
    public static void executeInput(String[] args) throws CompilationError {
        if(Runner.testing) return;

        System.out.print("PROGRAM INPUT : ");

        String name = scanner.nextLine();

        try {
            if(Runner.intStack.peek().containsElement(args[1]))
                Runner.intStack.peek().setValue(args[1], Integer.valueOf(name));
            else
                Runner.boolStack.peek().setValue(args[1], Boolean.valueOf(name));
        }catch (NumberFormatException e){
            throw new RuntimeException("Invalid Input Formating. Input Only Supports Numbers :(");
        }

    }

}
