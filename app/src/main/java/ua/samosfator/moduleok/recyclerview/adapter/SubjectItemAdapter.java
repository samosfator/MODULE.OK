package ua.samosfator.moduleok.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.parser.Subject;

public class SubjectItemAdapter extends RecyclerView.Adapter<SubjectItemAdapter.SubjectItemViewHolder> {

    private LayoutInflater inflater;
    private List<Subject> data = Collections.emptyList();

    private int[] drawableCircles = {
            R.drawable.circle_blue,
            R.drawable.circle_brown,
            R.drawable.circle_cyan,
            R.drawable.circle_deeppurple,
            R.drawable.circle_green,
            R.drawable.circle_grey,
            R.drawable.circle_indigo,
            R.drawable.circle_lightblue,
            R.drawable.circle_lightgreen,
            R.drawable.circle_lime,
            R.drawable.circle_orange,
            R.drawable.circle_pink,
            R.drawable.circle_purple,
            R.drawable.circle_red,
            R.drawable.circle_teal
    };
    private List<Integer> usedDrawables = new ArrayList<>();

    public SubjectItemAdapter(Context context, List<Subject> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public SubjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_subject_item, parent, false);
        SubjectItemViewHolder viewHolder = new SubjectItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectItemViewHolder holder, int position) {
        Subject current = data.get(position);
        holder.subjectName.setText(current.getName());
        holder.subjectDate.setText(current.getLastModule().getFormattedDate());
        holder.subjectScore.setText(String.valueOf(current.getLastModule().getScore()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SubjectItemViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectName;
        private TextView subjectScore;
        private TextView subjectDate;

        public SubjectItemViewHolder(View itemView) {
            super(itemView);
            subjectName = (TextView) itemView.findViewById(R.id.subject_name);
            subjectScore = (TextView) itemView.findViewById(R.id.subject_score);
            subjectScore.setBackgroundResource(getRandomUniqueDrawable());
            subjectDate = (TextView) itemView.findViewById(R.id.subject_date);
        }

        private int getRandomUniqueDrawable() {
            int randInt = new Random().nextInt(drawableCircles.length);
            while (usedDrawables.contains(randInt)) {
                randInt = new Random().nextInt(drawableCircles.length);
            }
            usedDrawables.add(randInt);
            return drawableCircles[randInt];
        }
    }
}
