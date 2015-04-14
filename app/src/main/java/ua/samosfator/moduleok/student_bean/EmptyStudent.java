package ua.samosfator.moduleok.student_bean;

import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.R;

public class EmptyStudent extends Student {
    public EmptyStudent() {
        name = App.getContext().getString(R.string.sampleStudentName);
        group = App.getContext().getString(R.string.sampleStudentGroup);
        firstSemester = new EmptySemester();
        secondSemester = new EmptySemester();
    }
}
