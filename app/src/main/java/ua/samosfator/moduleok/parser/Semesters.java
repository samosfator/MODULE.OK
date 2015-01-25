package ua.samosfator.moduleok.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;

public class Semesters implements Serializable {

    private ArrayList<Semester> semesters = new ArrayList<>(2);

    public Semesters(String html) {
        Document doc = Jsoup.parse(html);
        Elements semesterElements = doc.select(".items");
        addSemesters(semesterElements);
    }

    private void addSemesters(Elements semesterElements) {
        for (Element semesterElement : semesterElements) {
            if (semesterHasData(semesterElement)) {
                semesters.add(new Semester(semesterElement));
            } else {
                semesters.add(Semester.emptySemester());
            }
        }
    }

    private boolean semesterHasData(Element semesterElement) {
        return !semesterElement.text().contains("No results found.");
    }

    public Semester get(int index) {
        assert index <= 1 && index >= 0;
        return semesters.get(index);
    }

    public Semester getFirst() {
        return semesters.get(0);
    }

    public Semester getSecond() {
        return semesters.get(1);
    }
}
