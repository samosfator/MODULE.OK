package ua.samosfator.moduleok.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ua.samosfator.moduleok.notification.NewScoresService;
import ua.samosfator.moduleok.utils.App;

public class UpgradeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!App.isServiceRunning(NewScoresService.class)) {
            Log.d("UPGRADE_RECEIVER", "App is upgraded. Restarting the service!");
            context.startService(new Intent(context, NewScoresService.class));
        }
    }
}
