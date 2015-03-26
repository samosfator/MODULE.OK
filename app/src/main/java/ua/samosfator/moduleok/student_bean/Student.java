package ua.samosfator.moduleok.student_bean;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;

public class Student {

    protected String name;
    protected String group;

    protected Semester firstSemester;
    protected Semester secondSemester;

    public String getName() {
        return name;
    }

    public String getShortName() {
        String[] nameParts = getName().split(" ");
        try {
            return nameParts[1] + " " + nameParts[0];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return App.getContext().getString(R.string.sampleStudentName);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Semester getFirstSemester() {
        return firstSemester;
    }

    public void setFirstSemester(Semester firstSemester) {
        this.firstSemester = firstSemester;
    }

    public Semester getSecondSemester() {
        return secondSemester;
    }

    public void setSecondSemester(Semester secondSemester) {
        this.secondSemester = secondSemester;
    }

    public Semester getSemester(int index) {
        return index == 0 ? firstSemester : secondSemester;
    }

    public String getHashId() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String textToEncode = getName() + getGroup();
        byte[] encodedDigestBytes = digest.digest(textToEncode.getBytes("UTF-8"));

        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte encodedDigestByte : encodedDigestBytes) {
            hashStringBuilder.append(Integer.toString((encodedDigestByte & 0xff) + 0x100, 16).substring(1));
        }

        return hashStringBuilder.toString();
    }

    public static List<String> getSubjectsDifference(Student oneStudent, Student secondStudent, int semesterIndex) {
        List<String> modifiedSubjectNames = new ArrayList<>();

        List<Subject> subjectsOneStudent = oneStudent.getSemester(semesterIndex).getSubjects();
        List<Subject> subjectsSecondStudent = secondStudent.getSemester(semesterIndex).getSubjects();

        for (int i = 0; i < subjectsOneStudent.size(); i++) {
            Subject subjectOne = subjectsOneStudent.get(i);
            Subject subjectSecond = subjectsSecondStudent.get(i);
            subjectOne.getModules();

            int FirstSubjectSum = 0;
            int SecondSubjectSum = 0;

            List<Module> modules = subjectOne.getModules();
            for (int i1 = 0; i1 < modules.size(); i1++) {
                Module moduleOne = subjectOne.getModules().get(i1);
                Module moduleTwo = subjectSecond.getModules().get(i1);
                FirstSubjectSum += moduleOne.getScore();
                SecondSubjectSum += moduleTwo.getScore();
            }
            Log.d("calculating scores sum", FirstSubjectSum + ", " + SecondSubjectSum);
            if (FirstSubjectSum!=SecondSubjectSum){
                modifiedSubjectNames.add(subjectOne.getName());
            }
        }
        return modifiedSubjectNames;
    }
}
