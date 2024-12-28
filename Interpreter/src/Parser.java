import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.Scanner;


/**
 * The {@code Parser} class processes BASIC source code files.
 *
 * It reads the file and stores its content in a single string,
 * {@code code} and an array of lines {@code lines}.
 */
public class Parser{

    //List to store lines read from the file
    private List<String> fileLines = new ArrayList<>();

    //Array to hold processed lines
    private String[] lines;

    //We combine lines into one string
    private String code = "";

    //Name of the file to be parsed - fileName
    private String fileName;



        /**
         * Constructs a {@code Parser} instance with the given file name.
         * @param fileName the name of the file to be parsed
         */
        Parser(String fileName){
            this.fileName = fileName;
        }



    /**
 * Reads the file specified by {@code fileName} and processes its content.
 * Stores each line in a list and prepares the file for further evaluation.
 *
 * @throws CompilationError if an error occurs during file reading or processing
 */

    public void readFile() throws CompilationError, URISyntaxException {
        File file = new File(fileName);

        String name = String.valueOf(Parser.class.getProtectionDomain().getCodeSource().getLocation());

        name = name.substring(0, name.length() - 27);
        name += "Interpreter/CodesInBASIC/" + fileName;
        name = name.substring(6);

        // Try-with-resources to ensure the BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            String line;

            // Read each line and add it to the fileLines list
            while ((line = reader.readLine()) != null){
                fileLines.add(line);
            }

        } catch (FileNotFoundException e) {
            // Handle the case where the specified file does not exist
            System.out.println("File not found: " + name);

        } catch (IOException e) {
            // Handle errors that occur while reading the file
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }


        // Process the content into a single code string
        calcCode();


        // Prepare the lines for further processing
        evalLines();
    }



    /**
     * Combines all the non-empty lines from the file into one string,
     * adding a semicolon at the end of each line.
     */
    private void calcCode() {
        for (String str : fileLines) {
            String s = str.trim(); // Remove leading/trailing spaces
            if (!s.isEmpty())
                code += str + ";"; // Add a semicolon to the end of each non-empty line
        }
    }



    /**
     * Copies the lines from the {@code fileLines} list into the {@code lines} array.
     * This prepares the lines for output or further processing.
     */
    private void evalLines(){
        lines = new String[fileLines.size()];

        // Go through each line in fileLines and fill the lines array
        for(int i = 0; i < fileLines.size(); i++)
            lines[i] = fileLines.get(i);
    }



    /**
     * Returns an array containing all processed lines from the file.
     *
     * @return an array of strings representing the file's lines
     */
    public String getCode(){
        return code;
    }


    /**
     * Returns an array containing all processed lines from the file.
     *
     * @return an array of strings representing the file's lines
     */
    public String[] getLines(){
        return lines;
    }



    /**
     * Prints all the processed lines to the console.
     */

}
