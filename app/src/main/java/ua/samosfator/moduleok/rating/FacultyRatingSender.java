package ua.samosfator.moduleok.rating;

import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;

import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.Preferences;
import ua.samosfator.moduleok.R;

public class FacultyRatingSender {

    public static final String TOTAL_SCORE_KEY = "totalScore";
    private static Rating rating = new Rating();

    public static void sendTotalScoreOnStart() {
        if (!App.isLoggedIn()) {
            return;
        }
        if (isLaunchFirstTime()) {
            updateUserRating();
            try {
                sendTotalScore();

//                Toast.makeText(App.getContext(), "Faculty total score: " + rating.getTotalScore() + "↑", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();

//                Toast.makeText(App.getContext(), "Failed to send total score", Toast.LENGTH_SHORT).show();
            }
            Log.d(TOTAL_SCORE_KEY, "first launch - send total score");
        }
    }

    private static boolean isLaunchFirstTime() {
        return Preferences.read(TOTAL_SCORE_KEY, "").equals("");
    }

    private static void updateUserRating() {
        rating = RatingParser.getRating();
        storeTotalScore();
    }

    private static void storeTotalScore() {
        Preferences.save(TOTAL_SCORE_KEY, String.valueOf(rating.getTotalScore()));
    }

    public static void sendTotalScoreOnRefresh() {
        int previousScore = Integer.parseInt(Preferences.read(TOTAL_SCORE_KEY, "0"));
        updateUserRating();
        if (previousScore != rating.getTotalScore() || Boolean.valueOf(Preferences.read("lastSendFailed", "false"))) {
            int scoreDifference = rating.getTotalScore() - previousScore;
            try {
                sendTotalScore();
                Preferences.save("lastSendFailed", "false");

//                Toast.makeText(App.getContext(), "Faculty total score: " + scoreDifference + " ↑", Toast.LENGTH_SHORT).show();
                Log.d(TOTAL_SCORE_KEY, "send - total scores differ: " + previousScore + " / " + rating.getTotalScore());
            } catch (IOException e) {
                Preferences.save("lastSendFailed", "true");

//                Toast.makeText(App.getContext(), "Failed to send total score", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Log.d(TOTAL_SCORE_KEY, "same total scores: " + previousScore);
        }
    }

    private static void sendTotalScore() throws IOException {
        Jsoup.connect(rating.getSendUrl())
                .userAgent(App.getContext().getResources().getString(R.string.user_agent))
                .ignoreContentType(true)
                .get();
    }
}
