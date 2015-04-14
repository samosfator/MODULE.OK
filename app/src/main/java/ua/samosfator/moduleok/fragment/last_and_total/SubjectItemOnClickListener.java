package ua.samosfator.moduleok.fragment.last_and_total;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import ua.samosfator.moduleok.utils.Analytics;
import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.Utils;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.animation.AnimationFactory;
import ua.samosfator.moduleok.fragment.navigation_drawer.sections.RecyclerItemClickListener;
import ua.samosfator.moduleok.student_bean.Subject;

class SubjectItemOnClickListener implements RecyclerItemClickListener.OnItemClickListener {

    private List<Subject> mSubjects;
    private FragmentManager mFragmentManager;

    private TextView subjectTotalScoreTextView;
    private TextView subjectDate;
    private TextView subjectWeight;
    private int totalScore;
    private int lastScore;

    public SubjectItemOnClickListener(List<Subject> subjects, FragmentManager fragmentManager) {
        mSubjects = subjects;
        mFragmentManager = fragmentManager;
    }

    @Override
    public void onItemClick(View view, int position) {
//        toggleTotalScore(view, position);
//        FragmentUtils.showFragment(mFragmentManager.beginTransaction(), FragmentsKeeper.getSubject(mSubjects.get(position)));
        Analytics.trackEvent("Click", "Subject Item");
    }

    private void toggleTotalScore(View view, int position) {
        subjectTotalScoreTextView = (TextView) view.findViewById(R.id.subject_total_score);
        subjectDate = (TextView) view.findViewById(R.id.subject_date);
        subjectWeight = (TextView) view.findViewById(R.id.subject_weight);
        totalScore = mSubjects.get(position).getTotalScore();
        lastScore = mSubjects.get(position).getLastModule().getScore();

        animateSubjectTotalScoreChange(view);
        setSubjectScore();
        setSubjectTotalScoreBackground();
        toggleTotalScoreTypeface();
        toggleTotalMessage(position);
    }

    private void animateSubjectTotalScoreChange(View view) {
        ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.subject_score_view_flipper);
        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
    }

    private void setSubjectScore() {
        if (isTotalScoreView()) {
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    subjectTotalScoreTextView.setText(String.valueOf(lastScore)), 200);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    subjectTotalScoreTextView.setText(String.valueOf(totalScore)), 200);
        }
    }

    private boolean isTotalScoreView() {
        String totalScoreName = App.getContext().getString(R.string.total_score_name);
        return subjectDate.getText().equals(totalScoreName);
    }

    private void setSubjectTotalScoreBackground() {
        if (isTotalScoreView()) {
            int drawableDependsOnTotalScore = Utils.getScoreCircleDrawable(lastScore);
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    subjectTotalScoreTextView.setBackgroundResource(drawableDependsOnTotalScore), 200);

        } else {
            int drawableDependsOnTotalScore = Utils.getScoreCircleDrawable(totalScore);
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    subjectTotalScoreTextView.setBackgroundResource(drawableDependsOnTotalScore), 200);
        }
    }

    private void toggleTotalMessage(final int position) {
        String totalScoreName = App.getContext().getString(R.string.total_score_name);
        String subjectDateStr = mSubjects.get(position).getLastModule().getDate();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            subjectDate.setText(isTotalScoreView() ? subjectDateStr : totalScoreName);
            subjectWeight.setVisibility(isTotalScoreView() ? View.GONE : View.VISIBLE);
        }, 100);
    }

    private void toggleTotalScoreTypeface() {
        if (isTotalScoreView()) {
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    subjectTotalScoreTextView.setTypeface(null, Typeface.NORMAL), 200);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(() ->
                    subjectTotalScoreTextView.setTypeface(null, Typeface.BOLD), 200);
        }
    }
}
