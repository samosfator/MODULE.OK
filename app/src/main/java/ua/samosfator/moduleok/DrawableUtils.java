package ua.samosfator.moduleok;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawableUtils {

    final private static int[] drawableCircles = {
            R.drawable.circle_blue,
            R.drawable.circle_brown,
            R.drawable.circle_cyan,
            R.drawable.circle_deeppurple,
            R.drawable.circle_green,
            R.drawable.circle_grey,
            R.drawable.circle_indigo,
            R.drawable.circle_lightblue,
            R.drawable.circle_lightgreen,
            R.drawable.circle_lime,
            R.drawable.circle_orange,
            R.drawable.circle_pink,
            R.drawable.circle_purple,
            R.drawable.circle_red,
            R.drawable.circle_teal
    };
    private static List<Integer> usedDrawables = new ArrayList<>();

    public static int getDrawableDependsOnScore(int score) {
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

    public static int getRandomUniqueDrawable() {
        int randInt = new Random().nextInt(drawableCircles.length);
        while (usedDrawables.contains(randInt)) {
            randInt = new Random().nextInt(drawableCircles.length);
        }
        usedDrawables.add(randInt);
        return drawableCircles[randInt];
    }
}
