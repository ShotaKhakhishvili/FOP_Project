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

    // List to store lines read from the file
    private List<String> fileLines = new ArrayList<>();

    // Array to hold processed lines
    private String[] lines;

    // Single string combining all processed lines from the file
    private String code = "";

    // Name of the file to be parsed - fileName
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
 *
 * -- Reads each line from the file into a list
 * -- Converts the list into a single combined code string
 * -- Stores individual lines into an array for further evaluation
 *
 * @throws CompilationError    if an error occurs during file reading or processing.
 * @throws URISyntaxException if there is an issue with the file path.
 */
    public void readFile() throws CompilationError, URISyntaxException {
    File file = new File(fileName);

    // Adjust file path to locate the target file within the project structure
    String name = String.valueOf(Parser.class.getProtectionDomain().getCodeSource().getLocation());
    name = name.substring(0, name.length() - 27);
    name += "Interpreter/CodesInBASIC/" + fileName;
    name = name.substring(6);

    // Open file using BufferedReader, ensuring resources are closed automatically
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

        // Combine lines into a single string
        calcCode();

        // Prepare the lines for further processing
        evalLines();
    }



    /**
     * Combines all non-empty lines from the file into a single string.
     *
     * -- Trims unnecessary spaces from each line
     * -- Adds a semicolon to the end of each line
     * -- Updates the {@code code} field
     */
    private void calcCode() {
        for (String str : fileLines) {
            String s = str.trim(); // Remove leading/trailing spaces
            if (!s.isEmpty())
                code += str + ";"; // Add a semicolon to the end of each non-empty line
        }
    }



    /**
     * Converts the {@code fileLines} list into the {@code lines} array
     * -- This prepares the lines for output or further processing.
     */
    private void evalLines(){
        lines = new String[fileLines.size()];

        // Go through each line in fileLines and fill the lines array
        for(int i = 0; i < fileLines.size(); i++)
            lines[i] = fileLines.get(i);
    }



    /**
     * getter...
     * Returns the combined code as a single string.
     *
     * @return the code with all file lines joined, ending with semicolons.
     */
    public String getCode(){
        return code;
    }


    
    /**
     * getter...
     * Returns the lines from the file as an array.
     *
     * @return an array of strings, each representing a line from the file.
     */
    public String[] getLines(){
        return lines;
    }


    }


