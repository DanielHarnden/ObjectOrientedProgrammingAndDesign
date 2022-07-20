package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JSoupDemo {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://ttuss1.tntech.edu/PROD/bwskttuclasses.P_DispClasses").get();
        System.out.println(doc.title());

        Elements newsHeadlines = doc.select("select[name='my_subject'] option");
        for (int i = 1; i < newsHeadlines.size(); i++) {
            String headline = newsHeadlines.get(i).text();
            System.out.println(headline);
        }

    }

}
