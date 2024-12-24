import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    private List<String> fileLines = new ArrayList<>();
    private String[] lines;
    private String code = "";

    private String fileName;

    Parser(String fileName){
        this.fileName = fileName;
    }

    public void readFile() throws FileNotFoundException {

        File file = new File("Interpreter");
        String absolutePath = file.getAbsolutePath() + "\\src\\" + fileName;

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + absolutePath);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        calcCode();
        evalLines();
    }

    private void calcCode(){
        for(String str : fileLines){
            String s = str.trim();
            if(!s.isEmpty())
                code += str + ";";
        }
    }

    private void evalLines(){
        List<String> temp = new ArrayList<>();
        String curr = "";
        for(char ch : code.toCharArray()){
            if(ch == ';')
            {
                temp.add(curr);
                curr = "";
            }else{
                curr += ch;
            }
        }

        lines = new String[temp.size()];
        for(int i = 0; i < temp.size(); i++)
            lines[i] = temp.get(i);
    }

    public String[] getLines(){
        return lines;
    }
    public String getCode(){
        return code;
    }
    public void printLines(){
        for(String line : lines)
            System.out.println(line);
    }
}
