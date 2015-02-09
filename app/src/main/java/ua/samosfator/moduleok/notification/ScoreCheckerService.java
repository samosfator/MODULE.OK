package ua.samosfator.moduleok.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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

    public static int pendingModulesCount = NearbyModules.getCount();

    private Timer timer = new Timer("timer");
    private TimerTask updateTimeTask = new UpdateTimeTask();
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
        Toast.makeText(this, "Congrats! ScoreCheckerService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServiceTimer();

        Toast.makeText(this, "ScoreCheckerService Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
        return 0;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "ScoreCheckerService Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");

        stopServiceTimer();
    }

    public void onEvent(LoginEvent event) {
        App.registerClassForEventBus(this);
        startServiceTimer();
    }

    public void onEvent(InternetConnectionPresent event) {
        App.registerClassForEventBus(this);
        Log.d(TAG, "internet presence!");
        startServiceTimer();
    }

    public void onEvent(LogoutEvent event) {
        stopServiceTimer();
    }

    public void onEvent(InternetConnectionAbsent event) {
        Log.d(TAG, "internet absence!");
        stopServiceTimer();
    }

    private void startServiceTimer() {
        moduleDatesUpdateTask = new ModuleDatesUpdateTask();
        timer.schedule(moduleDatesUpdateTask, 200, TimeUnit.HOURS.toMillis(12));

        updateTimeTask = new UpdateTimeTask();
        ModuleDatesUpdateTask.updatePendingModulesCount();
        if (pendingModulesCount > 0) {
            timer.schedule(updateTimeTask, 0, TimeUnit.HOURS.toMillis(2));

            Log.d(TAG, "" + pendingModulesCount + " modules in the near 2 days");
        } else {
            Log.d(TAG, "No modules dates in the near 2 days");
        }
    }

    private void stopServiceTimer() {
        moduleDatesUpdateTask.cancel();
        updateTimeTask.cancel();
    }
}
