package ua.samosfator.moduleok.fragment.navigation_drawer_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.App;
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
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        DrawerSection currentSection = data.get(position);
        holder.section.setText(currentSection.getTitle());
        holder.section.setCompoundDrawablesWithIntrinsicBounds(currentSection.getIconId(), 0, 0, 0);
        if (position == 0) {
            highlightFirstSection(holder);
        }
    }

    private void highlightFirstSection(SectionViewHolder holder) {
        holder.section.setTextColor(App.getContext().getResources().getColor(R.color.colorAccent));
        holder.section.setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private Button section;

        public SectionViewHolder(View itemView) {
            super(itemView);
            section = (Button) itemView.findViewById(R.id.section_text);
        }
    }
}
