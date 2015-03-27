package ua.samosfator.moduleok.fragment.subjects_fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<Subject> data = Collections.emptyList();

    public SubjectItemAdapter(Context context, List<Subject> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public SubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_subject_item, parent, false);
        return new SubjectItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectItemViewHolder holder, int position) {
        Subject current = data.get(position);
        holder.subjectName.setText(current.getName());
        holder.subjectDate.setText(current.getModules().get(current.getModules().size() - 1).getDate());
        holder.subjectWeight.setText(current.getModules().get(current.getModules().size() - 1).getWeight() + "%");
//        holder.subjectControlType.setText(String.valueOf(current.getControlType().getControlName()));
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
            holder.subjectScore.setText(String.valueOf(current.getLastModule().getScore()));
            holder.subjectScore.setBackgroundResource(DrawableUtils.getScoreCircleDrawable(current.getLastModule().getScore()));
        }
    }

    private boolean areAllModulesPassed(int subjectIndex) {
        List<Module> modules = data.get(subjectIndex).getModules();
        boolean allModulesPassed = true;
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
