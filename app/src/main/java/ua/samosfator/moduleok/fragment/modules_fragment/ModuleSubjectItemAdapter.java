package ua.samosfator.moduleok.fragment.modules_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.DrawableUtils;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.parser.Module;
import ua.samosfator.moduleok.parser.Subject;

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
    public void onBindViewHolder(ModuleSubjectItemViewHolder holder, int position) {
        final Subject current = data.get(position);
        final List<Module> modules = current.getModules();
        final int modulesCount = modules.size();

        if (moduleIndex >= modulesCount) {
            holder.subjectItemLayout.removeAllViews();
            return;
        }

        final Module currentModule = modules.get(moduleIndex);

        holder.subjectName.setText(current.getName());
        holder.subjectDate.setText(currentModule.getFormattedDate());
        holder.subjectWeight.setText(String.valueOf(currentModule.getWeight() + "%"));
        holder.subjectScore.setText(String.valueOf(currentModule.getScore()));
        holder.subjectScore.setBackgroundResource(DrawableUtils.getDrawableDependsOnScore(currentModule.getScore()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ModuleSubjectItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout subjectItemLayout;

        private TextView subjectName;
        private TextView subjectScore;
        private TextView subjectDate;
        private TextView subjectWeight;

        public ModuleSubjectItemViewHolder(View itemView) {
            super(itemView);
            subjectItemLayout = (LinearLayout) itemView.findViewById(R.id.subject_item_layout);
            subjectName = (TextView) itemView.findViewById(R.id.subject_name);
            subjectScore = (TextView) itemView.findViewById(R.id.subject_score);
            subjectDate = (TextView) itemView.findViewById(R.id.subject_date);
            subjectWeight = (TextView) itemView.findViewById(R.id.subject_weight);
        }
    }
}
