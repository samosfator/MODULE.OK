package ua.samosfator.moduleok.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.event.UpdateTimeChangeEvent;

public class Semesters implements Serializable {

    private List<Semester> semesters = new ArrayList<>(2);

    public Semesters(String html) {
        Document doc = Jsoup.parse(html);
        Elements semesterElements = doc.select(".items");
        parseUpdateTime(doc.select(".updated_tag").first());
        addSemesters(semesterElements);
    }

    private void parseUpdateTime(Element updateTimeElement) {
        String rawUpdateTime = updateTimeElement.text().replace("updated: ", "");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault());
        try {
            App.setUpdateTime(dateFormat.parse(rawUpdateTime));
            postUpdateTimeChangeEvent();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void postUpdateTimeChangeEvent() {
        EventBus.getDefault().post(new UpdateTimeChangeEvent());
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
