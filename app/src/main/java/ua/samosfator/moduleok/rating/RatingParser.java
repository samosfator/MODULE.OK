package ua.samosfator.moduleok.rating;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.student_bean.Module;
import ua.samosfator.moduleok.student_bean.Subject;

public class RatingParser {

    //TODO Code smell
    public static Rating getRating() {
        Rating rating = new Rating();

        try {
            rating.setUserId(StudentKeeper.getStudent().getHashId());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        rating.setGroup(StudentKeeper.getStudent().getGroup());

        int currentSemesterIndex = Calendar.getInstance().get(Calendar.MONTH) > 7 ? 0 : 1;
        final int[] totalScore = {0};
        final int[] scoresCount = {0};
        final int[] modulesCount = {0};

        List<Subject> subjects = StudentKeeper.getStudent()
                .getSemester(currentSemesterIndex)
                .getSubjects();

        StreamSupport.stream(subjects)
                .map(subject -> {
                    modulesCount[0] += subject.getModules().size();
                    return subject;
                })
                .map(subject -> StreamSupport.stream(subject.getModules()))
                .map(modulesStream -> modulesStream.map(Module::getScore).collect(Collectors.toList()))
                .map(StreamSupport::stream)
                .forEach(scoresListStream -> scoresListStream.forEach(score -> {
                    totalScore[0] += score;
                    if (score != 0) {
                        scoresCount[0]++;
                    }
                }));

        rating.setTotalScore(totalScore[0]);
        rating.setScoresCount(scoresCount[0]);
        rating.setTotalScoreLimit(modulesCount[0]);
        rating.setSendUrl(getSendUrl(rating));

        return rating;
    }

    private static String getSendUrl(Rating rating) {
        try {
            String urlStr = "http://moduleok-api.meteor.com/api/rating/postTotalScore?";
            urlStr += "userId=" + rating.getUserId();
            urlStr += "&group=" + rating.getGroup();
            urlStr += "&totalScore=" + rating.getTotalScore();
            urlStr += "&scoresCount=" + rating.getScoresCount();
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();

            return url.toURI().toASCIIString();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
