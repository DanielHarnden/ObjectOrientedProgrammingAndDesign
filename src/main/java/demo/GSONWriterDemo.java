package demo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;


public class GSONWriterDemo {

    public static void main(String[] args){

        ArrayList<BeanDemo> db = new ArrayList();

        db.add(new BeanDemo("Jerry", "01", "prof"));
        db.add(new BeanDemo("Pikachu", "02", "pokemon"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(db);

        try {
            // First time you run this, it creates the file
            String filename = "testfile.json";
            File file = new File("src/main/resources/" + filename);

            if (file.createNewFile()) {
                FileWriter out = new FileWriter(file);
                out.write(jsonString);
                out.close();
                System.out.println("File is created!");
            } else {
                // The second time you run this, it reads the file
                FileReader in = new FileReader(file);
                // Do something with the file
                JsonReader jr = gson.newJsonReader(in);
                BeanDemo[] instance = gson.fromJson(jr, BeanDemo[].class);
                System.out.println("File already exists.");
            }
        } catch (IOException ex){

        }


    }

}
