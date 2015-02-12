package ua.samosfator.moduleok.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.event.InternetConnectionAbsent;
import ua.samosfator.moduleok.event.InternetConnectionPresent;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.event.LogoutEvent;

public class ScoreCheckerService extends Service {

    private final String TAG = "SERVICE";

    private long modulesDatesUpdateTaskPeriod = TimeUnit.HOURS.toMillis(12);
    private long scoreChangedTaskPeriod = TimeUnit.HOURS.toMillis(2);

    public static int pendingModulesCount = NearbyModules.getCount();

    private Timer timer = new Timer("timer");
    private TimerTask scoresChangedTask = new ScoresChangedTask();
    private TimerTask moduleDatesUpdateTask = new ModuleDatesUpdateTask();

    public ScoreCheckerService() {
        App.registerClassForEventBus(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        App.registerClassForEventBus(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServiceTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopServiceTimer();
    }

    public void onEvent(LoginEvent event) {
        App.registerClassForEventBus(this);
        startServiceTimer();
    }

    public void onEvent(InternetConnectionPresent event) {
        App.registerClassForEventBus(this);
        startServiceTimer();
    }

    public void onEvent(LogoutEvent event) {
        stopServiceTimer();
    }

    public void onEvent(InternetConnectionAbsent event) {
        stopServiceTimer();
    }

    private void startServiceTimer() {
        scheduleModuleDatesUpdateTask();
        scheduleScoresChangedTask();
    }

    private void scheduleModuleDatesUpdateTask() {
        moduleDatesUpdateTask = new ModuleDatesUpdateTask();
        timer.schedule(moduleDatesUpdateTask, 200, modulesDatesUpdateTaskPeriod);
    }

    private void scheduleScoresChangedTask() {
        scoresChangedTask = new ScoresChangedTask();
        ModuleDatesUpdateTask.updatePendingModulesCount();
        if (pendingModulesCount > 0) {
            Log.d(TAG, "" + pendingModulesCount + " modules in the near 2 days");
            timer.schedule(scoresChangedTask, 0, scoreChangedTaskPeriod);
        } else {
            Log.d(TAG, "No modules dates in the near 2 days");
        }
    }

    private void stopServiceTimer() {
        moduleDatesUpdateTask.cancel();
        scoresChangedTask.cancel();
    }
}
