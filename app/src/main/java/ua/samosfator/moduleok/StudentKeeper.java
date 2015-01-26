package ua.samosfator.moduleok;

public class StudentKeeper {

    private static Student student;
    private static int currentSemester;
    private static boolean semesterIsInit;

    public static Student getCurrentStudent() {
        if (student == null) {
            initStudent();
        }
        return student;
    }

    public static Student forceInitAndGetCurrentStudent() {
        initStudent();
        return student;
    }

    public static void initStudent() {
        student = new Student(PageLoader.getMainPageHtml(false));
    }

    public static void refreshStudent() {
        if (!Auth.isLoggedIn()) return;
        student = new Student(PageLoader.getMainPageHtml(true));
    }

    public static int getCurrentSemesterIndex() {
        if (!semesterIsInit) initSemesterIndex();
        return currentSemester;
    }

    public static void initSemesterIndex() {
        currentSemester = Integer.parseInt(Preferences.read("currentSemester", "0"));
        semesterIsInit = true;
    }

    public static void setCurrentSemesterIndex(int currentSemester) {
        StudentKeeper.currentSemester = currentSemester;
        Preferences.save("currentSemester", String.valueOf(currentSemester));
    }
}
