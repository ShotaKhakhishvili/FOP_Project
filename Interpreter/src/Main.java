import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws CompilationError {
        Runner runner = new Runner("program.txt");
        runner.run();
        while (!Compiler.outputQueue.isEmpty()){
            System.out.println(Compiler.outputQueue.poll());
        }
    }
}