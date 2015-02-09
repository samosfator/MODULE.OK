package ua.samosfator.moduleok.notification;

import java.util.List;
import java.util.TimerTask;

import java8.util.stream.StreamSupport;
import ua.samosfator.moduleok.SessionIdExpiredException;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.parser.Subject;

public class ScoresChangedTask extends TimerTask {
    @Override
    public void run() {
        if (areScoresChanged()) {
            ScoreCheckerNotification.sendNotification();
        }
    }

    private boolean areScoresChanged() {
        List<Subject> oldSubjects = getSubjects();
        try {
            StudentKeeper.refreshStudent();
        } catch (SessionIdExpiredException ignored) {
        }
        List<Subject> newSubjects = getSubjects();

        return getTotalSumAllScores(oldSubjects) != getTotalSumAllScores(newSubjects);
    }

    private int getTotalSumAllScores(List<Subject> subjects) {
        final int[] oldTotalScore = {0};
        StreamSupport.parallelStream(subjects)
                .map(subject -> StreamSupport.parallelStream(subject.getModules()))
                .map(modulesStream -> modulesStream.map(module -> oldTotalScore[0] += module.getScore()));
        return oldTotalScore[0];
    }

    private List<Subject> getSubjects() {
        return StudentKeeper.getCurrentStudent()
                .getSemesters()
                .get(StudentKeeper.getCurrentSemesterIndex())
                .getSubjects();
    }
}
