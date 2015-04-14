package ua.samosfator.moduleok.fragment.modules;

import android.content.Context;
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

public class ModuleSubjectItemAdapter extends RecyclerView.Adapter<ModuleSubjectItemAdapter.ModuleSubjectItemViewHolder> {

    private LayoutInflater inflater;
    private List<Subject> data = Collections.emptyList();
    private int moduleIndex;

    public ModuleSubjectItemAdapter(Context context, List<Subject> data, int module) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.moduleIndex = module;
    }

    @Override
    public ModuleSubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_subject_item, parent, false);
        return new ModuleSubjectItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(ModuleSubjectItemViewHolder holder, int position) {
        final Subject current = data.get(position);

        if (current.getName().equals(App.getContext().getString(R.string.second_semester_name))) {
            ((LinearLayout) holder.subjectName.getParent()).setPadding(DrawableUtils.dpToPx(16), DrawableUtils.dpToPx(0), 0, DrawableUtils.dpToPx(0));
            ((LinearLayout) holder.subjectName.getParent().getParent()).setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
            ViewGroup.LayoutParams layoutParams = ((LinearLayout) holder.subjectName.getParent().getParent()).getLayoutParams();
            layoutParams.height = DrawableUtils.dpToPx(35);
            holder.subjectName.setText(App.getContext().getString(R.string.second_semester_name));
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

        final List<Module> modules = current.getModules();
        final int modulesCount = modules.size();
        final boolean moduleIsPresent = moduleIndex < modulesCount;

        if (moduleIsPresent) {
            displayThisModule(holder, current, modules);
        } else {
            doNotDisplayThisSubject(holder);
        }
    }

    private void displayThisModule(ModuleSubjectItemViewHolder holder, Subject currentSubject, List<Module> modules) {
        final Module currentModule = modules.get(moduleIndex);

        holder.subjectName.setText(currentSubject.getName());
        holder.subjectDate.setText(currentModule.getDate());
        holder.subjectWeight.setText(currentModule.getWeight() + "%");
//        holder.subjectControlType.setText(String.valueOf(currentSubject.getControlType().getControlName() ));
        holder.subjectScore.setText(String.valueOf(currentModule.getScore()));
        holder.subjectScore.setBackgroundResource(DrawableUtils.getScoreCircleDrawable(currentModule.getScore()));
    }

    private void doNotDisplayThisSubject(ModuleSubjectItemViewHolder holder) {
        holder.subjectItemLayout.removeAllViews();
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
