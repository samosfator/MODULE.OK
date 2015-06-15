package ua.samosfator.moduleok.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java8.util.stream.StreamSupport;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.student_bean.Module;
import ua.samosfator.moduleok.student_bean.Subject;
import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.Utils;

class SubjectItemAdapter extends RecyclerView.Adapter<SubjectItemAdapter.SubjectItemViewHolder> {

    private LayoutInflater inflater;
    private int semesterDividerIndex = -100;
    private List<Subject> subjects = Collections.emptyList();
    private List<Module> data = new ArrayList<>();

    public SubjectItemAdapter(Context context, List<Subject> subjects, int semesterDividerIndex) {
        inflater = LayoutInflater.from(context);
        this.subjects = subjects;
    }

    @Override
    public SubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_subject_item, parent, false);
        return new SubjectItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectItemViewHolder holder, int position) {
        Module current = data.get(position);
        holder.subjectWeight.setText(current.getWeight() + "%");
        holder.subjectScore.setText(String.valueOf(current.getScore()));
        holder.subjectScore.setBackgroundResource(Utils.getScoreCircleDrawable(current.getScore()));
    }

    private boolean areAllModulesPassed(int subjectIndex) {
        List<Module> modules = subjects.get(subjectIndex).getModules();
        boolean allModulesPassed = true;
        if (modules == null) return false;
        for (Module module : modules) {
            if (module.getScore() == 0) {
                allModulesPassed = false;
            }
        }
        return allModulesPassed;
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
