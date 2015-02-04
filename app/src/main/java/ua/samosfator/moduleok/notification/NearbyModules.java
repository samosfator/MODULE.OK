package ua.samosfator.moduleok.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.parser.Module;
import ua.samosfator.moduleok.parser.Semester;
import ua.samosfator.moduleok.parser.Semesters;
import ua.samosfator.moduleok.parser.Subject;

public class NearbyModules {

    private static List<Date> modulesDates = new ArrayList<>();

    public static int getCount() {
        initModulesDatesList();
        sortModulesDatesList();
        filterPastDates();
        filterDatesInTwoDayPeriod();
        return modulesDates.size();
    }

    private static void initModulesDatesList() {
        modulesDates.clear();
        Semesters semesters = StudentKeeper.getCurrentStudent().getSemesters();
        int currentSemesterIndex = StudentKeeper.getCurrentSemesterIndex();
        Semester currentSemester = semesters.get(currentSemesterIndex);
        List<Subject> subjects = currentSemester.getSubjects();
        int subjectsCount = subjects.size();
        for (int i = 0; i < subjectsCount; i++) {
            Subject subject = subjects.get(i);
            List<Module> modules = subject.getModules();
            int modulesCount = modules.size();
            for (int j = 0; j < modulesCount; j++) {
                Module module = modules.get(j);
                modulesDates.add(module.getDate());
            }
        }
    }

    private static void sortModulesDatesList() {
        Collections.sort(modulesDates);
    }

    private static void filterPastDates() {
        List<Date> futureModuleDates = new ArrayList<>();
        for (Date modulesDate : modulesDates) {
            if (modulesDate.compareTo(new Date()) > 0) {
                futureModuleDates.add(modulesDate);
            }
        }
        modulesDates = futureModuleDates;
    }

    private static void filterDatesInTwoDayPeriod() {
        List<Date> nearFutureDates = new ArrayList<>();
        long currentTime = new Date().getTime();

        for (Date modulesDate : modulesDates) {
            long moduleTime = modulesDate.getTime();
            int twoDaysMilliseconds = 172800000;
            if ((moduleTime - currentTime) < twoDaysMilliseconds) {
                nearFutureDates.add(modulesDate);
            }
        }
        modulesDates = nearFutureDates;
    }
}
