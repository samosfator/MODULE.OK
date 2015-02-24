package ua.samosfator.moduleok.student_bean;

import java.util.Collections;

public class EmptySemester extends Semester {
    public EmptySemester() {
        subjects = Collections.emptyList();
    }
}
