import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class InputHandler {
    public static void executeInput(String[] args){
        String input = System.in.toString();
        try {
            Integer integerInput = Integer.valueOf(input);
        }catch (NumberFormatException e){
            throw new RuntimeException();
        }
    }

    private static void checkInputBefore(String[] args) throws CompilationError{
        
        if(args.length != 2 || Variables.checkValidity(args[0]))
            throw new CompilationError("Illegal Input Statement");
        if(!Runner.intStack.peek().containsElement(args[1])){
            throw new CompilationError("Attempt To Input Into An Undeclared Variable '" + args[1] + "'");
        }
    }

}
