import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws CompilationError, URISyntaxException {

        for(int i = 1; i <= 10; i++){
            Runner.Declare("algorithm" + i + ".txt");
            System.out.println("(" + i + ")____________Running an algorithm____________(" + i + ")");
            System.out.println();
            try{
                Runner.testing = true;
                Runner.run();
            }catch (RuntimeException e){
                if(!(Runner.intStack.size() > 1))
                    throw e;
            }

            Runner.Declare("algorithm" + i + ".txt");
            Runner.testing = false;
            Runner.run();

            System.out.println();
        }
    }
}