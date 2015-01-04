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

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private LayoutInflater inflater;
    private List<String> data = Collections.emptyList();

    public SectionAdapter(Context context, List<String> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_section_item, parent, false);
        SectionViewHolder viewHolder = new SectionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        String current = data.get(position);
        holder.title.setText(current);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;

        public SectionViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.section_text);
            icon = (ImageView) itemView.findViewById(R.id.section_icon);
        }
    }
}
