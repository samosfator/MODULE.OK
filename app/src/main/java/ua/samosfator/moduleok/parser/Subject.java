package ua.samosfator.moduleok.parser;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subject implements Serializable {

    private String name;
    private CONTROL_TYPE controlType;
    private List<Module> modules = new ArrayList<>();
    private int totalScore;

    private String sourceHtml;

    public String getName() {
        return name;
    }

    public CONTROL_TYPE getControlType() {
        return controlType;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getLastModule() {
        int lastModuleIndex = modules.size() - 1;
        while (modules.get(lastModuleIndex).getScore() == 0 && lastModuleIndex > 0) {
            lastModuleIndex--;
        }
        return modules.get(lastModuleIndex);
    }

    String getSourceHtml() {
        return sourceHtml;
    }

    void setName(String name) {
        this.name = name;
    }

    void setControlType(String strControlType) {
        if (strControlType.contains("Екзамен")) {
            setControlType(CONTROL_TYPE.EXAM);
        } else {
            setControlType(CONTROL_TYPE.CREDIT);
        }
    }

    void setControlType(CONTROL_TYPE controlType) {
        this.controlType = controlType;
    }

    void setTotalScore(String strTotalScore) {
        try {
            setTotalScore(Integer.parseInt(strTotalScore));
        } catch (NumberFormatException e) {
            setTotalScore(0);
            e.printStackTrace();
        }
    }

    void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    void setModules(List<Module> modules) {
        this.modules = modules;
    }

    void setSourceHtml(String html) {
        this.sourceHtml = html;
    }

    public static Subject fromHtml(String html, Semester semester) {
        Elements cells = Jsoup.parse(html, "", Parser.xmlParser()).select("td");

        Subject subject = new Subject();
        subject.setName(cells.eq(0).text());
        subject.setControlType(cells.eq(1).text());
        subject.setTotalScore(cells.last().text());
        subject.setSourceHtml(html);

        List<Module> modulesForSubject = semester.getModulesFor(subject);
        subject.setModules(modulesForSubject);

        return subject;
    }

    @Override
    public String toString() {
        return "\nSubject: \"" + name + "\" - " + controlType.getControlName() + " - " + totalScore + "\n\t\tModules(" + modules.size() + "): " + modules;
    }
}
