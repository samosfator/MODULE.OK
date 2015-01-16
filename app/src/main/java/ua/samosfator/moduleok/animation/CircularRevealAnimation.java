package ua.samosfator.moduleok.animation;

import android.graphics.Point;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import ua.samosfator.moduleok.App;

public class CircularRevealAnimation {

    public static void addForView(View view) {
        Point screenSize = App.getScreenSize();

        int cx = (screenSize.x / 2) + (screenSize.x / 3);
        int cy = (screenSize.y / 2) + (screenSize.y / 3);

        int finalRadius = Math.max(screenSize.x, screenSize.y);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }
}
