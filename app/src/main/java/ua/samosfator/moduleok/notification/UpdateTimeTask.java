package ua.samosfator.moduleok.notification;

import java.util.TimerTask;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.SessionIdExpiredException;
import ua.samosfator.moduleok.StudentKeeper;

public class UpdateTimeTask extends TimerTask {
    @Override
    public void run() {
        if (isUpdateTimeChanged()) {
            ScoreCheckerNotification.sendNotification();
        }
    }

    private boolean isUpdateTimeChanged() {
        String oldTime = App.getFormattedUpdateTime();
        String newTime = oldTime;

        try {
            StudentKeeper.refreshStudent();
        } catch (SessionIdExpiredException ignored) {
        }

        newTime = App.getFormattedUpdateTime();

        return !oldTime.equals(newTime);
    }
}
