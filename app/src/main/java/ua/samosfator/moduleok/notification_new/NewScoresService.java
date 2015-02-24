package ua.samosfator.moduleok.notification_new;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NewScoresService extends Service {

    private final String TAG = "SERVICE";

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
