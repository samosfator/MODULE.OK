package ua.samosfator.moduleok.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.MainActivity;
import ua.samosfator.moduleok.R;

public class ScoreCheckerNotification {

    private static NotificationCompat.Builder mNotificationBuilder;
    private static final int NOTIFICATION_ID = 1448;

    public static void sendNotification() {
        buildNotification();
        pushNotification();
    }

    private static void buildNotification() {
        mNotificationBuilder = new NotificationCompat.Builder(App.getContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(App.getContext().getString(R.string.notification_new_score_available_title_text))
                .setContentText(App.getContext().getString(R.string.notification_new_score_detailed_text));
        Intent resultIntent = new Intent(App.getContext(), MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        App.getContext(),
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mNotificationBuilder.setContentIntent(resultPendingIntent);
    }

    private static void pushNotification() {
        NotificationManager notificationManager =
                (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }
}
