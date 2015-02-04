package ua.samosfator.moduleok.notification;

import java.util.TimerTask;

import ua.samosfator.moduleok.StudentKeeper;

public class ModuleDatesUpdateTask extends TimerTask {
    @Override
    public void run() {
        updatePendingModulesCount();
    }

    public static void updatePendingModulesCount() {
        StudentKeeper.refreshStudent();
        ScoreCheckerService.pendingModulesCount = NearbyModules.getCount();
    }
}
