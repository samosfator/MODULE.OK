package ua.samosfator.moduleok;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import ua.samosfator.moduleok.parser.Semesters;

public class Student {

    private String mainPageHtml;

    private String name;
    private String surname;
    private String patronymic;

    private String group;

    private Semesters semesters;

    public Student(String mainPageHtml) {
        this.mainPageHtml = mainPageHtml;

        this.name = getName();
        this.surname = getSurName();
        this.patronymic = getPatronymic();

        this.group = getGroup();
        this.semesters = new Semesters(getMainPageDocument().html());

        store();
    }

    public static Student readSavedStudent() {
        String studentJson = Preferences.read("student", "");
        if (studentJson.length() > 10) {
            try {
                return (Student) JsonReader.jsonToJava(studentJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void store() {
        try {
            Preferences.save("student", JsonWriter.objectToJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getName() {
        if (this.name != null) return this.name;
        return getNameParts()[1];
    }

    private String getSurName() {
        if (this.surname != null) return this.surname;
        return getNameParts()[0];
    }

    private String getPatronymic() {
        if (this.patronymic != null) return this.patronymic;
        return getNameParts()[2];
    }

    private String getGroup() {
        if (this.group != null) return this.group;
        return getRawGroupName().substring(0, getRawGroupName().length() - 1);
    }

    private String getRawGroupName() {
        return getPageTitle().split("\\(")[1];
    }

    private String[] getNameParts() {
        return getFullStudentName().split(" ");
    }

    private String getFullStudentName() {
        return WordUtils.capitalizeFully(getPageTitle().split("\\(")[0].trim());
    }

    private String getPageTitle() {
        return getMainPageDocument().select(".pageTitle").text();
    }

    private Document getMainPageDocument() {
        return Jsoup.parse(mainPageHtml);
    }
}
