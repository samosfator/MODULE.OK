package ua.samosfator.moduleok.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Semester implements Serializable {

    private List<Subject> subjects;

    private transient Element semesterHtml;
    private int subjectsCount;
    private int maxModuleCount;

    public Semester(Element semesterHtml) {
        this.semesterHtml = semesterHtml;
    }

    private Semester() {
        //Required empty constructor
    }

    public static Semester emptySemester() {
        Semester semester = new Semester();
        semester.subjects = Collections.emptyList();
        semester.subjectsCount = 0;
        semester.semesterHtml = Jsoup.parse("");
        semester.maxModuleCount = 0;

        return semester;
    }

    public List<Subject> getSubjects() {
        if (subjects == null) {
            subjects = new ArrayList<>();
            for (int subjectIndex = 0, subjectsCount = getSubjectsCount(); subjectIndex < subjectsCount; subjectIndex++) {
                Subject subject = Subject.fromHtml(getSubjectHtml(subjectIndex), this);
                subjects.add(subject);
            }
        }
        return subjects;
    }

    public int getSubjectsCount() {
        if (subjectsCount == 0) {
            this.subjectsCount = semesterHtml.select("tr td:eq(0)").size();
        }
        return subjectsCount;
    }

    public int getMaxModuleCount() {
        if (maxModuleCount == 0) {
            for (int i = 0; i < getSubjectsCount(); i++) {
                if (subjects == null) getSubjects();
                if (subjects.size() == 0) return 0;

                int moduleCountForSubject = getModulesFor(subjects.get(i)).size();
                if (moduleCountForSubject > maxModuleCount) {
                    maxModuleCount = moduleCountForSubject;
                }
            }
        }
        return maxModuleCount;
    }

    public List<Module> getModulesFor(Subject subject) {
        List<Module> modules = new ArrayList<>();
        for (int moduleIndex = 0, modulesCount = getModulesCountFor(subject); moduleIndex < modulesCount; moduleIndex++) {
            Module module = Module.fromHtml(subject.getSourceHtml(), moduleIndex);
            modules.add(module);
        }

        return modules;
    }

    public int getModulesCountFor(Subject subject) {
        Elements subjectHtmlRow = semesterHtml.select("tr:contains(" + subject.getName() + ")");
        Elements cells = Jsoup.parse(subjectHtmlRow.html(), "", Parser.xmlParser()).select("td");
        @SuppressWarnings("UnnecessaryLocalVariable") int modulesCount = cutOddCellsFromSubjectRow(cells).size();
        return modulesCount;
    }

    static List<Elements> cutOddCellsFromSubjectRow(Elements cells) {
        List<Elements> requiredCells = new ArrayList<>();
        List<Element> trimmedCells = cells.subList(2, cells.size() - 1);

        for (int i = 0; i < trimmedCells.size(); i += 3) {
            Elements singleModuleHtml = new Elements(trimmedCells.subList(i, i + 3));
            if (singleModuleHtml.text().length() > 0) {
                requiredCells.add(singleModuleHtml);
            }
        }

        return requiredCells;
    }

    private String getSubjectHtml(int subjectRowIndex) {
        return this.semesterHtml.select("tr").eq(subjectRowIndex + 1).html();
    }
}