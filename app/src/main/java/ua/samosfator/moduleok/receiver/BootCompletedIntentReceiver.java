package ua.samosfator.moduleok.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ua.samosfator.moduleok.notification.NewScoresService;
import ua.samosfator.moduleok.utils.App;

public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!App.isServiceRunning(NewScoresService.class) && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, NewScoresService.class));
        }
    }
}
