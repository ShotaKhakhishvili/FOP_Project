import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser parser = new Parser("program.txt");

        parser.readFile();
        parser.printLines();

        Variables<Integer> ints = new Variables<>();
        ints.declareVariable("a", 5);
        ints.declareVariable("b", 3);
        ints.setValue("a", 12 + ints.getValue("b"));
        System.out.println(ints.getValue("a"));
        JavaImplementations_Shota implementationsShota = new JavaImplementations_Shota();
    }
}