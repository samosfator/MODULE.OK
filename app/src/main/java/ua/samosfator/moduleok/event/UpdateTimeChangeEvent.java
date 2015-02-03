package ua.samosfator.moduleok.event;

import java.util.List;

import ua.samosfator.moduleok.parser.Semester;

public class UpdateTimeChangeEvent {

    private List<Semester> semesters;

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }
}
