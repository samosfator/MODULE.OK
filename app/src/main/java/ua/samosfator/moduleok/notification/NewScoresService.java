package ua.samosfator.moduleok.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.student_bean.Student;

public class NewScoresService extends Service {

    private final String TAG = "SERVICE";
    private static final long REPEAT_TIME = 1000 * 5;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service on start");

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Log.d("Scheduler", "scheduler execution");

            Student oldStudent = StudentKeeper.getStudent();
            StudentKeeper.initStudentFromLogin();
            Student newStudent = StudentKeeper.getStudent();

            List<String> subjectsDifference = Student.getSubjectsDifference(oldStudent, newStudent, StudentKeeper.getCurrentSemesterIndex());

            Log.d("SCHEDULER", subjectsDifference.toString());

            if (subjectsDifference.size() != 0) {
                String notificationText = constructNotificationText(subjectsDifference);
                NewScoreNotification.sendNotification(notificationText);
            }
        }, 0, 10, TimeUnit.HOURS);

        return START_STICKY;
    }

    private String constructNotificationText(List<String> subjectsDifference) {
        String notificationText = "";
        for (int i = 0; i < subjectsDifference.size(); i++) {
            String subjectName = subjectsDifference.get(i);
            if (i == 0 || i == subjectsDifference.size() - 1) {
                notificationText += subjectName;
            } else {
                notificationText += subjectName + ", ";
            }
        }
        return notificationText;
    }
}
