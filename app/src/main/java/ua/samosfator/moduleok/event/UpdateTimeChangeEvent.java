package ua.samosfator.moduleok.event;

import java.util.List;

import ua.samosfator.moduleok.parser.Semester;
import ua.samosfator.moduleok.parser.Subject;

public class UpdateTimeChangeEvent {

    private List<Semester> semesters;

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }
}
