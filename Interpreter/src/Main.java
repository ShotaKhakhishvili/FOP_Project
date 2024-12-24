import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException  {
        Runner runner = new Runner("program.txt");
        runner.run();
    }
}