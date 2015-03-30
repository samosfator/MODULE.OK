package ua.samosfator.moduleok;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.LoadPageCompleteEvent;
import ua.samosfator.moduleok.student_bean.EmptyStudent;
import ua.samosfator.moduleok.student_bean.Student;

public class StudentKeeper {

    private static Student student;
    private static int currentSemester;
    private static boolean semesterIsInit;

    public static Student getStudent() {
        Log.d("STUDENT_KEEPER", "Invoking getStudent()");
        if (student == null) {
            Log.d("STUDENT_KEEPER", "student == null: " + String.valueOf(student == null));
            initStudentFromRefresh();
        }
        return student;
    }

    public static void initStudentFromRefresh() {
        Log.d("STUDENT_KEEPER", "initStudentFromRefresh()");
        try {
            student = new GetScoresJsonAsyncTask().execute().get();
            Log.d("STUDENT_KEEPER", "Posting LoadPageCompleteEvent");
            EventBus.getDefault().post(new LoadPageCompleteEvent());
        } catch (Exception e) {
            e.printStackTrace();
            student = new EmptyStudent();
        }
    }

    public static void initStudentFromLogin() {
        Log.d("STUDENT_KEEPER", "initStudentFromLogin()");
        try {
            student = new GetScoresJsonAsyncTask().execute().get();
            Log.d("STUDENT_KEEPER", "isfl student == null: " + String.valueOf(student == null));
        } catch (Throwable e) {
            e.printStackTrace();
            student = new EmptyStudent();
        }
    }

    public static void initStudentFromBackup(String json) {
        Log.d("STUDENT_KEEPER", "initStudentFromBackup()");
        try {
            student = new Gson().fromJson(json, new TypeToken<Student>() {}.getType());

        } catch (Exception e) {
            e.printStackTrace();
            student = new EmptyStudent();
        }
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
