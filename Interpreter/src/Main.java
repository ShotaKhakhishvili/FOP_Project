import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws CompilationError, URISyntaxException {

        if(args.length > 0){
            runProgram(args[0]);
        }
        else
        for(int i = 1; i <= 10; i++){
            System.out.println("(" + i + ")____________Running an algorithm____________(" + i + ")");
            System.out.println();

            runProgram("algorithm" + i + ".txt");

            System.out.println();
        }
    }

    static void runProgram(String programName) throws CompilationError, URISyntaxException {
        Runner.Declare(programName);
        try{
            Runner.testing = true;
            Runner.run();
        }catch (RuntimeException e){
            if(!(Runner.intStack.size() > 1))
                throw e;
        }

        Runner.Declare(programName);
        Runner.testing = false;
        Runner.run();
    }
}