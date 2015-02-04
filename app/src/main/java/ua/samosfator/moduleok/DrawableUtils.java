package ua.samosfator.moduleok;

public class DrawableUtils {

    public static int getScoreCircleDrawable(int score) {
        if (score >= 90 && score <= 100) {
            return R.drawable.circle_5;
        } else if (score >= 75 && score < 90) {
            return R.drawable.circle_4;
        } else if (score >= 60 && score < 75) {
            return R.drawable.circle_3;
        } else {
            return R.drawable.circle_2;
        }
    }
}
