package ua.samosfator.moduleok.fragment.last_total_fragment;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.DrawableUtils;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.animation.AnimationFactory;
import ua.samosfator.moduleok.parser.Subject;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

class SubjectItemOnClickListener implements RecyclerItemClickListener.OnItemClickListener {

    private List<Subject> mSubjects;

    private TextView subjectTotalScoreTextView;
    private TextView subjectDate;
    private TextView subjectWeight;
    private int totalScore;
    private int lastScore;

    public SubjectItemOnClickListener(List<Subject> subjects) {
        mSubjects = subjects;
    }

    @Override
    public void onItemClick(View view, int position) {
        subjectTotalScoreTextView = (TextView) view.findViewById(R.id.subject_total_score);
        subjectDate = (TextView) view.findViewById(R.id.subject_date);
        subjectWeight = (TextView) view.findViewById(R.id.subject_weight);
        totalScore = mSubjects.get(position).getTotalScore();
        lastScore = mSubjects.get(position).getLastModule().getScore();


        setSubjectScore();
        setSubjectTotalScoreBackground();
        animateSubjectTotalScoreChange(view);
        toggleTotalScoreTypeface();
        toggleTotalMessage(position);
    }

    private void setSubjectScore() {
        if (isTotalScoreView()) {
            subjectTotalScoreTextView.setText(String.valueOf(lastScore));
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    subjectTotalScoreTextView.setText(String.valueOf(totalScore));
                }
            }, 200);
        }
    }

    private boolean isTotalScoreView() {
        String totalScoreName = App.getContext().getString(R.string.total_score_name);
        return subjectDate.getText().equals(totalScoreName);
    }

    private void setSubjectTotalScoreBackground() {
        if (isTotalScoreView()) {
            int drawableDependsOnTotalScore = DrawableUtils.getScoreCircleDrawable(lastScore);
            subjectTotalScoreTextView.setBackgroundResource(drawableDependsOnTotalScore);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    int drawableDependsOnTotalScore = DrawableUtils.getScoreCircleDrawable(totalScore);
                    subjectTotalScoreTextView.setBackgroundResource(drawableDependsOnTotalScore);
                }
            }, 200);
        }
    }

    private void animateSubjectTotalScoreChange(View view) {
        ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.subject_score_view_flipper);
        AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
    }

    private void toggleTotalMessage(int position) {
        String totalScoreName = App.getContext().getString(R.string.total_score_name);
        String subjectDateStr = mSubjects.get(position).getLastModule().getFormattedDate();
        subjectDate.setText(isTotalScoreView() ? subjectDateStr : totalScoreName);
        subjectWeight.setVisibility(isTotalScoreView() ? View.GONE : View.VISIBLE);
    }

    private void toggleTotalScoreTypeface() {
        if (isTotalScoreView()) {
            subjectTotalScoreTextView.setTypeface(null, Typeface.NORMAL);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    subjectTotalScoreTextView.setTypeface(null, Typeface.BOLD);
                }
            }, 200);
        }
    }
}
