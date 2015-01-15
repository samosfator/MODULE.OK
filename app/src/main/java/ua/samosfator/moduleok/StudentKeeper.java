package ua.samosfator.moduleok;

import android.util.Log;

public class StudentKeeper {

    private static Student student;

    public static void initStudent() {
        student = new Student(PageLoader.getMainPageHtml());
    }

    public static void refreshStudent() {
        if (!Auth.isLoggedIn()) return;
        student = new Student(PageLoader.getMainPageHtml());
    }

    public static Student getCurrentStudent() {
        if (student == null) {
            initStudent();
        }
        return student;
    }
}
