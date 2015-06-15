package ua.samosfator.moduleok.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ua.samosfator.moduleok.R;

public class Utils {

    public static int getScoreCircleDrawable(int score) {
        if (score >= 90 && score <= 100) {
            return R.drawable.circle_5;
        } else if (score >= 75 && score < 90) {
            return R.drawable.circle_4;
        } else if (score >= 60 && score < 75) {
            return R.drawable.circle_3;
        } else if (score > 0 && score < 60) {
            return R.drawable.circle_2;
        } else {
            return R.drawable.circle_0;
        }
    }

    public static String getModuleNameFor(int moduleIndex) {
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

    public static String getSectionNameFor(Class fragmentClass) {
        String className = fragmentClass.getSimpleName();
        switch (className) {
            case "LastTotalFragment":
                return App.getContext().getString(R.string.home_section);
            case "ModulesFragment":
                return App.getContext().getString(R.string.modules_section);
            case "DetailedSubjectsFragment":
                return App.getContext().getString(R.string.detailed_subjects_section);
            case "LoginFragment":
                return App.getContext().getString(R.string.login_section);
            default:
                return App.getContext().getString(R.string.app_name);
        }
    }

    public static String getDaysLeftToModule(String date) {
        int daysLeft = getDaysLeft(date);
        String daysLeftStr = String.valueOf(daysLeft);

        String dniv = App.getContext().getString(R.string.days);
        String dni = App.getContext().getString(R.string.dni);
        String den = App.getContext().getString(R.string.den);
        String daysCase;
        String lastChar = daysLeftStr.substring(daysLeftStr.length() - 1);

        if (lastChar.equals("1")) {
            daysCase = den;
        } else if ((lastChar.equals("2") || lastChar.equals("3") || lastChar.equals("4"))
                && (Math.abs(daysLeft) < 10 || Math.abs(daysLeft) > 20)) {
            daysCase = dni;
        } else {
            daysCase = dniv;
        }
        boolean isUkrainian = dniv.contains("і");

        if (daysLeft > 0) {
            if (isUkrainian) {
                return App.getContext().getString(R.string.in_future_period) + " " + daysLeft + " " + daysCase;
            } else {
                return App.getContext().getString(R.string.in_future_period) + " " + daysLeft + " " + App.getContext().getString(R.string.days);
            }
        } else {
            if (isUkrainian) {
                return -daysLeft + " " + daysCase + " " + App.getContext().getString(R.string.ago);
            } else {
                return -daysLeft + " " + App.getContext().getString(R.string.days_ago);
            }
        }
    }

    private static int getDaysLeft(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        int daysLeft = 0;
        try {
            Date parsedDate = simpleDateFormat.parse(date);
            daysLeft = (int) getDateDiff(parsedDate, new Date(), TimeUnit.DAYS);
        } catch (ParseException ignored) {
        }
        return daysLeft;
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMs = date1.getTime() - date2.getTime();
        return timeUnit.convert(diffInMs, TimeUnit.MILLISECONDS);
    }
}
