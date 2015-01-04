package ua.samosfator.moduleok.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.Serializable;

public class Semesters implements Serializable {

    private Semester first;
    private Semester second;

    public Semesters(String html) {
        Document doc = Jsoup.parse(html);
        Elements semesterElements = doc.select(".items");

        first = new Semester(semesterElements.get(0));
        second = new Semester(semesterElements.get(1));
    }

    public Semester getFirst() {
        return first;
    }

    public Semester getSecond() {
        return second;
    }
}
