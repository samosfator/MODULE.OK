package ua.samosfator.moduleok.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import ua.samosfator.moduleok.StudentKeeper;
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
        List<Subject> subjects = getCurrentSemesterSubjects();
        System.setProperty("java8.util.Spliterators.assume.oracle.collections.impl", "false");
        StreamSupport.stream(subjects)
                .map(Subject::getModules)
                .map(StreamSupport::stream)
                .map(modulesStream ->
                                modulesStream.map(module -> modulesDates.add(module.getDate()))
                );
    }

    private static List<Subject> getCurrentSemesterSubjects() {
        Semesters semesters = StudentKeeper.getCurrentStudent().getSemesters();
        int currentSemesterIndex = StudentKeeper.getCurrentSemesterIndex();
        Semester currentSemester = semesters.get(currentSemesterIndex);
        return currentSemester.getSubjects();
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
