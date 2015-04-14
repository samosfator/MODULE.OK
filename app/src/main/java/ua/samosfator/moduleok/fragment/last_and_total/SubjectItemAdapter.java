package ua.samosfator.moduleok.fragment.last_and_total;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.DrawableUtils;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.student_bean.Module;
import ua.samosfator.moduleok.student_bean.Subject;

class SubjectItemAdapter extends RecyclerView.Adapter<SubjectItemAdapter.SubjectItemViewHolder> {

    private LayoutInflater inflater;
    private int semesterDividerIndex = -100;
    private List<Subject> data = Collections.emptyList();

    public SubjectItemAdapter(Context context, List<Subject> data, int semesterDividerIndex) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.semesterDividerIndex = semesterDividerIndex;
    }

    @Override
    public SubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_subject_item, parent, false);
        return new SubjectItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectItemViewHolder holder, int position) {
        Subject current = data.get(position);

        try {
            holder.subjectName.setText(current.getName());
        } catch (NullPointerException e) {
            holder.subjectName.setText("");
        }
        try {
            holder.subjectDate.setText(current.getModules().get(current.getModules().size() - 1).getDate());
        } catch (NullPointerException e) {
            holder.subjectDate.setText("");
        }
        try {
            holder.subjectWeight.setText(current.getModules().get(current.getModules().size() - 1).getWeight() + "%");
        } catch (NullPointerException e) {
            holder.subjectWeight.setText("");
        }
//        holder.subjectControlType.setText(String.valueOf(current.getControlType()));
        if (semesterDividerIndex == position) {
            ((LinearLayout) holder.subjectName.getParent()).setPadding(DrawableUtils.dpToPx(16), DrawableUtils.dpToPx(0), 0, DrawableUtils.dpToPx(0));
            ((LinearLayout) holder.subjectName.getParent().getParent()).setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
            ViewGroup.LayoutParams layoutParams = ((LinearLayout) holder.subjectName.getParent().getParent()).getLayoutParams();
            layoutParams.height = DrawableUtils.dpToPx(35);
            holder.subjectName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.subjectName.setTextColor(App.getContext().getResources().getColor(R.color.grey_500));

            holder.subjectDate.setVisibility(View.GONE);
            holder.subjectWeight.setVisibility(View.GONE);
            holder.subjectScore.setVisibility(View.GONE);

            return;
        } else {
            ((LinearLayout) holder.subjectName.getParent().getParent()).setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_100));
            ViewGroup.LayoutParams layoutParams = ((LinearLayout) holder.subjectName.getParent().getParent()).getLayoutParams();
            layoutParams.height = DrawableUtils.dpToPx(65);
            holder.subjectName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            holder.subjectName.setTextColor(App.getContext().getResources().getColor(R.color.grey_1000b));

            holder.subjectDate.setVisibility(View.VISIBLE);
            holder.subjectWeight.setVisibility(View.VISIBLE);
            holder.subjectScore.setVisibility(View.VISIBLE);
        }

        if (areAllModulesPassed(position)) {
            holder.subjectDate.setText(App.getContext().getString(R.string.total_score_name));
            holder.subjectWeight.setVisibility(View.GONE);
            holder.subjectScore.setText(String.valueOf(current.getTotalScore()));
            holder.subjectScore.setTypeface(holder.subjectScore.getTypeface(), Typeface.BOLD);
            if (isTotalScoreView(holder)) {
                holder.subjectScore.setBackgroundResource(DrawableUtils.getScoreCircleDrawable(current.getTotalScore()));
            } else {
                holder.subjectScore.setBackgroundResource(DrawableUtils.getScoreCircleDrawable(current.getLastModule().getScore()));
            }
        } else {
            try {
                holder.subjectScore.setText(String.valueOf(current.getLastModule().getScore()));
            } catch (NullPointerException e) {
                holder.subjectScore.setText("");
            }
            try {
                holder.subjectScore.setBackgroundResource(DrawableUtils.getScoreCircleDrawable(current.getLastModule().getScore()));
            } catch (NullPointerException e) {
                holder.subjectScore.setBackgroundResource(DrawableUtils.getScoreCircleDrawable(0));;
            }
        }
    }

    private boolean areAllModulesPassed(int subjectIndex) {
        List<Module> modules = data.get(subjectIndex).getModules();
        boolean allModulesPassed = true;
        if (modules == null) return false;
        for (Module module : modules) {
            if (module.getScore() == 0) {
                allModulesPassed = false;
            }
        }
        return allModulesPassed;
    }

    private boolean isTotalScoreView(SubjectItemViewHolder holder) {
        String totalScoreName = App.getContext().getString(R.string.total_score_name);
        return holder.subjectDate.getText().equals(totalScoreName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SubjectItemViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectName;
        private TextView subjectScore;
        private TextView subjectDate;
        private TextView subjectWeight;
        private TextView subjectControlType;

        public SubjectItemViewHolder(View itemView) {
            super(itemView);
            subjectName = (TextView) itemView.findViewById(R.id.subject_name);
            subjectScore = (TextView) itemView.findViewById(R.id.subject_score);
            subjectDate = (TextView) itemView.findViewById(R.id.subject_date);
            subjectWeight = (TextView) itemView.findViewById(R.id.subject_weight);
            subjectControlType = (TextView) itemView.findViewById(R.id.subject_control_type);
        }
    }
}
