package ua.samosfator.moduleok.student_bean;

import java.util.List;

public class Semester {

    protected List<Subject> subjects;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public int getMaxModuleCount() {
        int maxModuleCount = 0;
        for (Subject subject : subjects) {
            if (subject.getModules().size() > maxModuleCount) {
                maxModuleCount = subject.getModules().size();
            }
        }
        return maxModuleCount;
    }
}
