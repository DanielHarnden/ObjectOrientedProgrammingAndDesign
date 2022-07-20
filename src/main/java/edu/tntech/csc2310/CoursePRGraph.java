package edu.tntech.csc2310;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CoursePRGraph {

    private final AtomicLong counter = new AtomicLong();

    /**
     * prerequisites service returns a json array formatted as [[ from, to, weight], *]. Java objects are
     * automatically serialized into JSON objects. , defaultValue = "CSC"
     * @param prefix
     * @return
     */
    @GetMapping("/prerequisites")
    public CoursePR prerequisites(@RequestParam(value = "prefix") String prefix) {

        ArrayList<HashMap<String,String>> adjlist = GenerateAdjacencyList(prefix);
        CoursePR cpr = new CoursePR(counter.incrementAndGet(), adjlist);
        return cpr;

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CoursePRGraph.class.getName());

    /**
     * fetchData demonstrates how to read file data on the server from the resources directory
     * @return
     */
    private ArrayList<HashMap<String, String>> GenerateAdjacencyList(String prefix){

        ArrayList<HashMap<String, String>> adjList = new ArrayList<>();

        CourseCatalog catalog = null;
        try {
            catalog = new CourseCatalog(prefix, "202180");
        } catch (CatalogNotFoundException e) {
            e.printStackTrace();
        }
        //
        ArrayList<Course> list = catalog.getCourses();
        for (Course c: list){
            String[] prereqs = c.getPrerequisites();
            String toCourse = c.getSubject() + " " + c.getNumber();
            if (prereqs != null){
                for (int j = 0; j < prereqs.length; j++) {
                    String fromCourse = prereqs[j];
                    HashMap<String, String> map = new HashMap<>();
                    map.put("from", fromCourse.trim());
                    map.put("to", toCourse);
                    map.put("weight", "1");
                    // Change this to display html for a more complete description
                    map.put("title", "<div><a class=\"button is-small\">" + fromCourse.trim() + " > " + toCourse + "</a></div>");
                    adjList.add(map);
                }
            }
        }
        return adjList;
    }
}
