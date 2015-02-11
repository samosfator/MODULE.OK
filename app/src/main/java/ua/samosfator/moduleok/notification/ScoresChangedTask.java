package ua.samosfator.moduleok.notification;

import java.util.List;
import java.util.TimerTask;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import ua.samosfator.moduleok.SessionIdExpiredException;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.parser.Module;
import ua.samosfator.moduleok.parser.Subject;

public class ScoresChangedTask extends TimerTask {
    @Override
    public void run() {
        if (areScoresChanged()) {
            ScoreCheckerNotification.sendNotification();
        }
    }

    private boolean areScoresChanged() {
        int totalSumAllOldScores = getScoresSum();

        try {
            StudentKeeper.refreshStudent();
        } catch (SessionIdExpiredException ignored) { }

        int totalSumAllNewScores = getScoresSum();

        return totalSumAllOldScores != totalSumAllNewScores;
    }

    private int getScoresSum() {
        List<Subject> subjects = getSubjects();
        return getTotalSumAllScores(subjects);
    }

    private List<Subject> getSubjects() {
        return StudentKeeper.getCurrentStudent()
                .getSemesters()
                .get(StudentKeeper.getCurrentSemesterIndex())
                .getSubjects();
    }

    private int getTotalSumAllScores(List<Subject> subjects) {
        final int[] oldTotalScore = {0};
        StreamSupport.stream(subjects)
                .map(subject -> StreamSupport.stream(subject.getModules()))
                .map(modulesStream -> modulesStream.map(Module::getScore).collect(Collectors.toList()))
                .map(StreamSupport::stream)
                .forEach(scoresListStream -> scoresListStream.forEach(score -> oldTotalScore[0] += score));
        return oldTotalScore[0];
    }
}
