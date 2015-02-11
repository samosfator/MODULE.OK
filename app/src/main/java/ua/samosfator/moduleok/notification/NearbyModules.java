package ua.samosfator.moduleok.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
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
        List<Module> modules = getCurrentSemesterModules();
        modulesDates = StreamSupport.stream(modules)
                .map(Module::getDate)
                .collect(Collectors.toList());
    }

    private static List<Module> getCurrentSemesterModules() {
        Semesters semesters = StudentKeeper.getCurrentStudent().getSemesters();
        int currentSemesterIndex = StudentKeeper.getCurrentSemesterIndex();
        Semester currentSemester = semesters.get(currentSemesterIndex);
        List<Subject> subjects = currentSemester.getSubjects();
        List<Module> modules = new ArrayList<>();
        for (Subject subject : subjects) {
            modules.addAll(subject.getModules());
        }
        return modules;
    }

    private static void sortModulesDatesList() {
        Collections.sort(modulesDates);
    }

    private static void filterPastDates() {
        modulesDates = StreamSupport.stream(modulesDates)
                .filter(modulesDate ->
                        modulesDate.compareTo(new Date()) > 0).collect(Collectors.toList()
                );
    }

    private static void filterDatesInTwoDayPeriod() {
        long currentTime = new Date().getTime();
        int twoDaysMilliseconds = 172800000;

        modulesDates = StreamSupport.stream(modulesDates)
                .filter(modulesDate -> (modulesDate.getTime() - currentTime) < twoDaysMilliseconds)
                .collect(Collectors.toList());
    }
}
