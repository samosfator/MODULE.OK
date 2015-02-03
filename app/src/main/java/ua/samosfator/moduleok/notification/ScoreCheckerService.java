package ua.samosfator.moduleok.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ScoreCheckerService extends Service {

    private ScoreChecker scoreChecker;
    private final String TAG = "SERVICE";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");

        scoreChecker = new ScoreChecker();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
        return 0;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");

        scoreChecker.refreshStudentTask.cancel();
        scoreChecker.moduleDatesUpdateTask.cancel();
    }
}
