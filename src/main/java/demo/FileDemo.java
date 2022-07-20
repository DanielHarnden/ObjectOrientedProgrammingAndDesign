package demo;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileDemo {

    public static void main(String[] args){

        try {
            //
            String filename = "testfile";
            File file = new File("src/main/resources/" + filename);

            if (file.createNewFile()) {
                FileWriter out = new FileWriter(file);
                out.write("My String");
                out.close();
                System.out.println("File is created!");
            } else {
                FileReader in = new FileReader(file);
                // Do something with the file
                System.out.println("File already exists.");
            }
        } catch (IOException ex){

        }
    }
}
