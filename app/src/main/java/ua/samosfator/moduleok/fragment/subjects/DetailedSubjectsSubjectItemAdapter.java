package ua.samosfator.moduleok.fragment.subjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.Utils;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.student_bean.Module;
import ua.samosfator.moduleok.student_bean.Subject;

public class DetailedSubjectsSubjectItemAdapter extends RecyclerView.Adapter<DetailedSubjectsSubjectItemAdapter.ModuleSubjectItemViewHolder> {

    private LayoutInflater inflater;
    private Subject mSubject;

    public DetailedSubjectsSubjectItemAdapter(Context context, Subject subject) {
        this.inflater = LayoutInflater.from(context);
        this.mSubject = subject;
    }

    @Override
    public ModuleSubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_subject_item, parent, false);
        return new ModuleSubjectItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mSubject.getModules().size();
    }

    @Override
    public void onBindViewHolder(ModuleSubjectItemViewHolder holder, int position) {
        Module module = mSubject.getModules().get(position);
        boolean moduleIsExam = false;
        if (mSubject.getMostValuableModule().equals(module) && mSubject.getControlType().equals("Екзамен")) {
            moduleIsExam = true;
        }
        displayThisModule(holder, module, position, moduleIsExam);
    }

    private void displayThisModule(ModuleSubjectItemViewHolder holder, Module module, int position, boolean moduleIsExam) {
        int leftDp = Utils.dpToPx(16);
        int topDp = Utils.dpToPx(11);
        int bottomDp = Utils.dpToPx(-11);

        holder.subjectDate.setText(module.getWeight() + "%");
        holder.subjectWeight.setText(Utils.getDaysLeftToModule(module.getDate()));

        if (moduleIsExam) {
            holder.subjectName.setText(module.getDate() + " (" + App.getContext().getString(R.string.exam_naming) + ")");
        } else if (module.getDate().equals("total")) { //view presents a total score
            holder.subjectName.setText(App.getContext().getString(R.string.total_score_name));
            removeDateWeightViews(holder, leftDp, topDp, bottomDp);
        } else if (module.getDate().equals("total-zalik")) {
            holder.subjectName.setText(App.getContext().getString(R.string.zalik_score_name));
            removeDateWeightViews(holder, leftDp, topDp, bottomDp);
        } else {
            holder.subjectName.setText(module.getDate());
        }
//        holder.subjectControlType.setText(String.valueOf(currentSubject.getControlType().getControlName() ));
        holder.subjectScore.setText(String.valueOf(module.getScore()));
        holder.subjectScore.setBackgroundResource(Utils.getScoreCircleDrawable(module.getScore()));
    }

    private void removeDateWeightViews(ModuleSubjectItemViewHolder holder, int leftDp, int topDp, int bottomDp) {
        ((LinearLayout) holder.subjectName.getParent().getParent()).setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
        ((LinearLayout) holder.subjectName.getParent()).setPadding(leftDp, topDp, 0, bottomDp);

        ((LinearLayout) holder.subjectDate.getParent()).removeView(holder.subjectDate);
        ((LinearLayout) holder.subjectWeight.getParent()).removeView(holder.subjectWeight);
        ((LinearLayout) holder.subjectControlType.getParent()).removeView(holder.subjectControlType);
    }

    class ModuleSubjectItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout subjectItemLayout;

        private TextView subjectName;
        private TextView subjectScore;
        private TextView subjectDate;
        private TextView subjectWeight;
        private TextView subjectControlType;

        public ModuleSubjectItemViewHolder(View itemView) {
            super(itemView);
            subjectItemLayout = (LinearLayout) itemView.findViewById(R.id.subject_item_layout);
            subjectName = (TextView) itemView.findViewById(R.id.subject_name);
            subjectScore = (TextView) itemView.findViewById(R.id.subject_score);
            subjectDate = (TextView) itemView.findViewById(R.id.subject_date);
            subjectWeight = (TextView) itemView.findViewById(R.id.subject_weight);
            subjectControlType = (TextView) itemView.findViewById(R.id.subject_control_type);
        }
    }
}
