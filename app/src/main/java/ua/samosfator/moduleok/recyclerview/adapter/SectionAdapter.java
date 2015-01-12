package ua.samosfator.moduleok.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.recyclerview.DrawerSection;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private LayoutInflater inflater;
    private List<DrawerSection> data = Collections.emptyList();

    public SectionAdapter(Context context, List<DrawerSection> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_section_item_drawer, parent, false);
        SectionViewHolder viewHolder = new SectionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        DrawerSection currentSection = data.get(position);
        holder.title.setText(currentSection.getTitle());
        holder.title.setCompoundDrawablesWithIntrinsicBounds(currentSection.getIconId(), 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public SectionViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.section_text);
        }
    }
}
