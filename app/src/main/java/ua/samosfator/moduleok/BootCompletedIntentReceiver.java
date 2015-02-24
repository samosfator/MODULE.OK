package ua.samosfator.moduleok;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ua.samosfator.moduleok.notification_new.NewScoresService;


public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!App.isServiceRunning(NewScoresService.class) && App.isLoggedIn()) {
            context.startService(new Intent(context, NewScoresService.class));
        }
    }
}
