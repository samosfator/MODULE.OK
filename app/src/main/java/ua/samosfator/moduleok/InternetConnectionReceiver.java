package ua.samosfator.moduleok;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.InternetConnectionAbsent;
import ua.samosfator.moduleok.event.InternetConnectionPresent;

public class InternetConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (App.hasInternetConnection()) {
            EventBus.getDefault().post(new InternetConnectionPresent());
        } else {
            EventBus.getDefault().post(new InternetConnectionAbsent());
        }
    }
}
