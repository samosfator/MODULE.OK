package ua.samosfator.moduleok.parser;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Module implements Serializable {

    private String subjectName;
    private int weight;
    private Date date;
    private int score;

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public String getSubjectName() {
        return subjectName;
    }

    void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getWeight() {
        return weight;
    }

    void setWeight(String strWeight) {
        setWeight(Integer.parseInt(strWeight));
    }

    void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFormattedDate() {
        return dateFormat.format(getDate());
    }

    public Date getDate() {
        return date;
    }

    void setDate(String strDate) {
        try {
            setDate(dateFormat.parse(strDate));
        } catch (ParseException e) {
            setDate(new Date());
            e.printStackTrace();
        }
    }

    void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    void setScore(String strScore) {
        try {
            setScore(Integer.parseInt(strScore));
        } catch (NumberFormatException e) {
            setScore(0);
            System.err.println("Empty score text, setting the value to 0");
        }
    }

    void setScore(int score) {
        this.score = score;
    }


    public static Module fromHtml(String html, int moduleIndex) {
        Elements cells = Jsoup.parse(html, "", Parser.xmlParser()).select("td");
        List<Elements> modulesCells = Semester.cutOddCellsFromSubjectRow(cells);

        Module module = new Module();
        module.setSubjectName(cells.eq(0).text());
        module.setWeight(modulesCells.get(moduleIndex).eq(0).text());
        module.setDate(modulesCells.get(moduleIndex).eq(1).text());
        module.setScore(modulesCells.get(moduleIndex).eq(2).text());

        return module;
    }

    @Override
    public String toString() {
        return "Module: " + weight + "% - " + dateFormat.format(date) + " - " + score;
    }
}
