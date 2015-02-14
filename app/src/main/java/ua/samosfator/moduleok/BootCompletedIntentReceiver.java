package ua.samosfator.moduleok;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ua.samosfator.moduleok.notification.ScoreCheckerService;

public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!App.isServiceRunning(ScoreCheckerService.class) && Auth.isLoggedIn()) {
            context.startService(new Intent(context, ScoreCheckerService.class));
        }
    }
}
