package edu.tntech.csc2310;

import com.google.gson.stream.JsonReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.*;

/**
 * The CourseCatalog class is used to scrape data from the Tennessee Tech online catalog.
 * CourseCatalog is dependent on the Course class.
 */
public class CourseCatalog {

    /**
     * db stores an array of Course objects.
     * catalogYear is the specified catalog year in format yyyyss
     *    where yyyy is the four digit year and ss is the semester code.
     * subject is the subject code.
     */
    private ArrayList<Course> db;
    private String catalogYear;
    private String subject;

    /**
     * @return catalogYear - returns the catalog year
     */
    public String getCatalogYear() { return catalogYear; }

    /**
     * @return subject - returns the subject code
     */
    public String getSubject() { return subject; }

    /**
     * @return db - returns the ArrayList of Course objects that is the catalog database
     */
    public ArrayList<Course> getCourses(){ return db; }

    /**
     * The CourseCatalog constructor takes a catalog code and a catalog year and generates an ArrayList of
     *    Course objects scraped from the TN Tech catalog website.
     * @param subject - the inputted catalog code.
     * @param catalogYear - the inputted catalog year.
     * @throws CatalogNotFoundException - throws this exception in the event that no catalog exists
     *    with the inputted subject code and / or catalog year.
     */
    public CourseCatalog(String subject, String catalogYear) throws CatalogNotFoundException {

        // Formats the catalog code and year to be read.
        String subj = subject.trim().toUpperCase();
        Integer trm = Integer.parseInt(catalogYear.trim());

        // Assigns the inputted values to the corresponding values in the CourseCatalog object.
        this.catalogYear = trm.toString();
        this.subject = subj.toUpperCase();
        // Initializes db and a list of course names and numbers from the TN Tech website to be used later.
        this.db = new ArrayList();
        ArrayList<String> list = CourseCatalog.getCourseNumbers(this.subject, this.catalogYear);

        // Creates a json file where the CourseCatalog will be read from or written to.
        String fileName = subj + "_" + trm.toString() + ".json";
        File newFile = new File("src/main/resources/" + fileName);

        // Catches an IO Exception
        try {
            // Checks to see if the file exists. If it does not exist...
            if (newFile.createNewFile())
            {
                // If the list of course names and numbers has values...
                if (list.size() > 0) {
                    // Loops through the list, attempting to create Course objects and catching
                    // CourseNotFound exceptions and adding those Courses to db.
                    for (String s : list) {
                        Course c = null;
                        try {
                            c = new Course(this.subject, s, this.catalogYear);
                            this.db.add(c);
                        } catch (CourseNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    // Writes the new db object to a json file that can be read later.
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(db);
                    FileWriter outputFile = new FileWriter(newFile);
                    outputFile.write(jsonString);
                    outputFile.close();

                // If the list does not have values...
                } else {
                    // Sets all CourseCatalog values to null and throws a CatalogNotFound exception.
                    this.subject = null;
                    this.catalogYear = null;
                    this.db = null;
                    newFile.delete();
                    CatalogNotFoundException cataEx = new CatalogNotFoundException("No catalog found with the subject " + subj + " or the term year " + trm.toString());
                    throw cataEx;
                }

            // If the file does exist...
            } else {
                // Reads the json data to the db ArrayList.
                FileReader inputFile = new FileReader(newFile);
                Gson gson = new Gson();
                JsonReader jReader = gson.newJsonReader(inputFile);
                Course[] fileInstance = gson.fromJson(jReader, Course[].class);

                for (int i = 0; i < fileInstance.length; i++)
                {
                    this.db.add(fileInstance[i]);
                }
                inputFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a Course object with the given course number.
     * @param number - the inputted course number to be searched for.
     * @return result - the found Course. If a Course does not exist, result is null.
     */
    public Course getCourse(String number){
        Course result = null;
        for (Course c: db){
            if (c.getNumber().equalsIgnoreCase(number)){
                result = c;
                break;
            }
        }
        return result;
    }

    /**
     * @return a string version of the db data.
     */
    public String toString(){
        return this.db.toString();
    }

    /**
     * Generates a list of course numbers to be used in instantiating Courses.
     * @param subject - the inputted catalog code.
     * @param catalogYear - the inputted catalog year.
     * @return list - a list of course numbers.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static ArrayList<String> getCourseNumbers(String subject, String catalogYear){

        Document doc = null;
        ArrayList<String> list = new ArrayList();
        try {
            doc = Jsoup.connect("https://ttuss1.tntech.edu/PROD/bwckctlg.p_display_courses?sel_crse_strt=1000&sel_crse_end=4999&sel_subj=&sel_levl=&sel_schd=&sel_coll=&sel_divs=&sel_dept=&sel_attr="+"&term_in="+catalogYear+"&one_subj="+subject).get();
            Elements courseTitles = doc.select(".nttitle");
            for (Element title : courseTitles) {
                String line = title.text();
                Scanner scan = new Scanner(line);
                scan.useDelimiter(" ");
                scan.next();
                String crseNum = scan.next();
                list.add(crseNum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }

}