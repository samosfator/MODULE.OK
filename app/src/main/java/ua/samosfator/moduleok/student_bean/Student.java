package ua.samosfator.moduleok.student_bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
}
