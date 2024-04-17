import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        // Creates a reader instance which takes
        // input from standard input - keyboard
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter a number: ");
        String password = "1234";
        // nextInt() reads the next integer from the keyboard
        int number = reader.nextInt();
        // println() prints the following line to the output screen
        System.out.println("You entered: " + number);

        // Call the function to read a user-specified file
        readFile();

        // Close the scanner
        reader.close();
    }

    public static void readFile() {
        Scanner fileReader = null;
        try {
            // Prompt the user for the file path
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter the file path: ");

            String filePath = reader.nextLine();

            // Create a File object with the specified file path
            File file = new File(filePath); // User input

            // Create a Scanner object to read the file
            fileReader = new Scanner(file); 

            // Read and print the contents of the file
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } finally {
            // Close the file reader
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }
}
