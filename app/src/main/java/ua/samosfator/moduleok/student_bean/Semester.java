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

    public static Subject getSubjectByModule(List<Subject> subjects, Module module) {
        Subject soughtSubject = null;
        for (Subject subject : subjects) {
            for (Module m : subject.getModules()) {
                if (m.getWeight() == module.getWeight()
                        && m.getDate().equals(module.getDate())
                        && m.getScore() == module.getScore()) {
                    soughtSubject = subject;
                }
            }
        }
        return soughtSubject;
    }
}
