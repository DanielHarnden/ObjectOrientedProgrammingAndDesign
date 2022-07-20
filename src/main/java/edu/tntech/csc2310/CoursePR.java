package edu.tntech.csc2310;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CoursePR is a placeholder object used in serialization of data into
 * a JSON format returned by the web service.
 */
public class CoursePR {

    /**
     * Stores the generated id for the project
     */
    private final long id;

    /**
     * Stores an adjacency list for a graph
     */
    private final ArrayList<HashMap<String, String>> adjlist;

    /**
     *
     * @param id - automatically generated id for the object
     * @param adjlist - the adjacency list of the graph
     */
    public CoursePR(long id, ArrayList<HashMap<String, String>> adjlist) {
        this.id = id;
        this.adjlist = adjlist;
    }

    /**
     *
     * @return adjlist - the adjacency list of the given graph
     */
    public ArrayList<HashMap<String, String>> getAdjacencyList() {
        return adjlist;
    }

    /**
     *
     * @return id - the id of the object
     */
    public long getId() {
        return id;
    }

}
