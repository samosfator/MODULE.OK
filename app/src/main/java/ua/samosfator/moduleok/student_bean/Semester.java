package ua.samosfator.moduleok.student_bean;

import java8.util.stream.StreamSupport;

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

    public static Subject getSubjectByModule(List<Subject> subjects, Module module) {
        Subject soughtSubject = null;
        for (Subject subject : subjects) {
            for (Module m : subject.getModules()) {
                if (m.equals(module)) {
                    soughtSubject = subject;
                }
            }
        }
        return soughtSubject;
    }

    public double getAverageScore() {
        return StreamSupport.stream(subjects)
                .mapToDouble(Subject::getAverageScore)
                .average()
                .getAsDouble();
    }
}
