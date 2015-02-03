package ua.samosfator.moduleok.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.MainActivity;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.SessionIdExpiredException;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.parser.Module;
import ua.samosfator.moduleok.parser.Semester;
import ua.samosfator.moduleok.parser.Semesters;
import ua.samosfator.moduleok.parser.Subject;

public class ScoreChecker {

    private final String TAG = "SCORE_CHECKER";

    private static List<Date> modulesDates = new ArrayList<>();

    private Timer scoreCheckTimer = new Timer("scoreCheckTimer");
    private Timer moduleDatesUpdateTimer = new Timer("moduleDatesUpdateTimer");
    TimerTask refreshStudentTask;
    TimerTask moduleDatesUpdateTask;

    public ScoreChecker() {
        App.registerClassForEventBus(this);
        moduleDatesUpdateTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("UpdateTimeChangeEvent");

                StudentKeeper.refreshStudent();
                initModulesDatesList();
                sortModulesDatesList();
                filterPastDates();
                filterDatesInTwoDayPeriod();
                startNewScheduledChecking();
            }
        };
        if (Auth.isLoggedIn()) {
            moduleDatesUpdateTimer.schedule(moduleDatesUpdateTask, 0, 12 * 60 * 60 * 1000);
        }
    }

    private void initModulesDatesList() {
        modulesDates.clear();
        Semesters semesters = StudentKeeper.getCurrentStudent().getSemesters();
        int currentSemesterIndex = StudentKeeper.getCurrentSemesterIndex();
        Semester currentSemester = semesters.get(currentSemesterIndex);
        List<Subject> subjects = currentSemester.getSubjects();
        int subjectsCount = subjects.size();
        for (int i = 0; i < subjectsCount; i++) {
            Subject subject = subjects.get(i);
            List<Module> modules = subject.getModules();
            int modulesCount = modules.size();
            for (int j = 0; j < modulesCount; j++) {
                Module module = modules.get(j);
                modulesDates.add(module.getDate());
            }
        }
    }

    private void sortModulesDatesList() {
        Collections.sort(modulesDates);
    }

    private void filterPastDates() {
        List<Date> futureModuleDates = new ArrayList<>();
        for (Date modulesDate : modulesDates) {
            if (modulesDate.compareTo(new Date()) > 0) {
                futureModuleDates.add(modulesDate);
            }
        }
        modulesDates = futureModuleDates;
    }

    private void filterDatesInTwoDayPeriod() {
        List<Date> nearFutureDates = new ArrayList<>();
        long currentTime = new Date().getTime();

        for (Date modulesDate : modulesDates) {
            long moduleTime = modulesDate.getTime();
            int twoDaysMilliseconds = 172800000;
            if ((moduleTime - currentTime) < twoDaysMilliseconds) {
                nearFutureDates.add(modulesDate);
            }
        }
        modulesDates = nearFutureDates;
    }

    private void startNewScheduledChecking() {
        if (modulesDates.size() > 0) {
            Log.i(TAG, "More than 0 module dates in the near future. Scheduling timer task.");
            if (refreshStudentTask != null) refreshStudentTask.cancel();
            refreshStudentTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "refreshing student...");
                    String oldTime = App.getFormattedUpdateTime();
                    try {
                        StudentKeeper.refreshStudent();
                    } catch (SessionIdExpiredException ignored) { }
                    String newTime = App.getFormattedUpdateTime();

                    Log.i(TAG, oldTime + " - " + newTime);
                    if (!oldTime.equals(newTime)) {
                        ScoreCheckerNotification.sendNotification();
                    }
                }
            };
            scoreCheckTimer.schedule(refreshStudentTask, 0, 10000);
            System.out.println(scoreCheckTimer);
        } else {
            Log.i(TAG, "No modules dates in the near 2 days");
        }
    }

    public void onEvent(LoginEvent event) {
        moduleDatesUpdateTimer.schedule(moduleDatesUpdateTask, 0, 12 * 60 * 60 * 1000);
    }
}
