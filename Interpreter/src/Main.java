import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Parser parser = new Parser("program.txt");

        parser.readFile();
        parser.printLines();
        Instruction a = Instruction.assignment;
        Assignment b = Assignment.add;
        System.out.println(b);
    }
}