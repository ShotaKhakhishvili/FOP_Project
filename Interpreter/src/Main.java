import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws Exception {
        Runner runner = new Runner("program.txt");
        runner.run();
    }
}