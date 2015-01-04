package ua.samosfator.moduleok.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.parser.Subject;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private LayoutInflater inflater;
    private List<Subject> data = Collections.emptyList();

    public SubjectsAdapter(Context context, List<Subject> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_subject_item, parent, false);
        SubjectViewHolder viewHolder = new SubjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject current = data.get(position);
        holder.subjectName.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectName;
        private ImageView icon;

        public SubjectViewHolder(View itemView) {
            super(itemView);

        }
    }
}
