package ua.samosfator.moduleok;

public class StudentKeeper {

    private static Student student;
    private static int currentSemester;
    private static boolean semesterInited;

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

    public static int getCurrentSemesterIndex() {
        if (!semesterInited) initSemesterIndex();
        return currentSemester;
    }

    public static void setCurrentSemesterIndex(int currentSemester) {
        StudentKeeper.currentSemester = currentSemester;
        Preferences.save("currentSemester", String.valueOf(currentSemester));
    }

    public static void initSemesterIndex() {
        currentSemester = Integer.parseInt(Preferences.read("currentSemester", "0"));
        semesterInited = true;
    }
}
