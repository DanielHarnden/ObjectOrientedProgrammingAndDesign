package edu.tntech.csc2310;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * The Course class is used to scrape data from the Tennessee Tech online catalog.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Course {

    /**
     * url is a link to the website to be scraped.
     * subject is the subject code.
     * number is the course number.
     * title is the course title.
     * description is the description of the course.
     * credits is the amount of credits for the course.
     * prerequisites is an array of strings with information about the course prerequisites.
     */

    private static final String url = "https://ttuss1.tntech.edu/PROD/bwckctlg.p_disp_course_detail?";
    private String subject;
    private String number;
    private String title;
    private String description;
    private int credits;
    private String[] prerequisites;

    /**
     * The Course constructor takes a course code, course number, and a course year and generates a Course object scraped from the TN Tech catalog website.
     * @param subject - the inputted course code.
     * @param number - the inputted course number.
     * @param term - the inputted course term.
     * @throws CourseNotFoundException - throws this exception in the event that no course exists with the inputted parameters.
     */
    public Course(String subject, String number, String term) throws CourseNotFoundException {
        // Formats the inputted variables to be read.
        String subj = subject.trim().toUpperCase();
        String numb = number.trim();
        Integer trm = Integer.parseInt(term.trim());
        Integer numbTest = Integer.parseInt(numb);
        // Generates the course URL
        String searchUrl = url + "&cat_term_in=" + trm.toString() + "&subj_code_in=" + subj + "&crse_numb_in=" + numbTest.toString();
        // Catches an IO Exception
        try {
            // Uses JSOUP to scrape the generated URL
            Document doc = Jsoup.connect(searchUrl).get();
            Elements elements = doc.select(".nttitle");
            // Generates a Course with the scraped data
            if (elements.size() > 0) {
                String temp = (String) elements.get(0).text();
                int index = temp.indexOf('-');
                this.title = temp.substring(index + 2);

                Elements courseDescription = doc.select(".ntdefault");
                this.description = courseDescription.get(0).text();
                this.subject = subj;
                this.number = numb;
                this.credits = (int) this.parseCRH();
            // If no Course exists, all values are set to null and a CourseNotFound exception is thrown
            } else {
                this.subject = null;
                this.description = null;
                this.number = null;
                this.credits = -1;

                CourseNotFoundException courEx = new CourseNotFoundException("No course found with the subject " + subj + ", the term year " + trm.toString() + " or the number " + numb);
                throw courEx;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double parseCRH(){
        int index = this.description.indexOf("Credit hours");
        String tmp = this.description.substring(0, index-1);
        int first = tmp.lastIndexOf(" ");
        int idx = Math.max(first, 0);
        tmp = tmp.substring(idx).trim();
        return Double.parseDouble(tmp);
    }

    /**
     * @return subject - returns the course subject.
     */
    public String getSubject() { return subject; }

    /**
     * @return number - returns the course number.
     */
    public String getNumber() { return number; }

    /**
     * @return title - returns the course title.
     */
    public String getTitle() { return title; }

    /**
     * @return description - returns the course description.
     */
    public String getDescription() { return description; }

    /**
     * Creates a flattened list of pre-requisites; removes C or D or better information,
     * as well as disjunctive normal form. All structure should be removed from the pre-requisite list
     * @return
     */
    public String[] getPrerequisites() {

        String[] repls = {
                "Course or Test: ",
                "Minimum Grade of C ",
                "Minimum Grade of D ",
                "May not be taken concurrently.",
                "May be taken concurrently.",
                "(",
                ")"
        };

        String[] list = null;
        if (this.description != null) {
            int sindex = this.description.lastIndexOf("Requirements:");
            if (sindex > 0) {
                String subStr = this.description.substring(sindex + 13).trim();
                for (int i = 0; i < repls.length; i++) {
                    subStr = subStr.replace(repls[i], "");
                }
                subStr = subStr.replace("or", ",");
                subStr = subStr.replace("and", ",");
                list = subStr.split(",");
            }
        }
        return list;
    }

    /**
     * @return credits - returns the course credits.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * @return a string of all the Course values
     */
    public String toString(){ return subject + " " + number + " " + title + "\n" + description; }

    public String toString(boolean full){
        if (full)
            return this + this.description;
        else
            return this.toString();
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }

}
