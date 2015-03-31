package ua.samosfator.moduleok;

public class DrawableUtils {

    public static int getScoreCircleDrawable(int score) {
        if (score >= 90 && score <= 100) {
            return R.drawable.circle_5;
        } else if (score >= 75 && score < 90) {
            return R.drawable.circle_4;
        } else if (score >= 60 && score < 75) {
            return R.drawable.circle_3;
        } else if (score > 0 && score < 60){
            return R.drawable.circle_2;
        } else {
            return R.drawable.circle_0;
        }
    }

    public static String getModuleName(int moduleIndex) {
        if (moduleIndex == 0) {
            return App.getContext().getString(R.string.module_1_name);
        } else if (moduleIndex == 1) {
            return App.getContext().getString(R.string.module_2_name);
        } else if (moduleIndex == 2) {
            return App.getContext().getString(R.string.module_3_name);
        } else if (moduleIndex == 3) {
            return App.getContext().getString(R.string.module_4_name);
        } else if (moduleIndex == 4) {
            return App.getContext().getString(R.string.module_5_name);
        } else if (moduleIndex == 5) {
            return App.getContext().getString(R.string.module_6_name);
        } else {
            return ".";
        }
    }

    public static int dpToPx(int dp) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
