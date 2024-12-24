import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws FileNotFoundException  {
        Runner runner = new Runner("program.txt");
        runner.run();
    }
}