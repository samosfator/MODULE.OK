package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private LayoutInflater inflater;
    private List<SectionDrawer> data = Collections.emptyList();

    public SectionAdapter(Context context, List<SectionDrawer> data) {
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
        SectionDrawer currentSection = data.get(position);
        if (position == SectionsEnum.EMPTY.INDEX) {
            makeViewADivider(holder);
            return;
        }
        holder.section.setText(currentSection.getTitle());
        holder.section.setCompoundDrawablesWithIntrinsicBounds(currentSection.getIconId(), 0, 0, 0);
        highlightSection(holder, position);
    }

    private void makeViewADivider(SectionViewHolder holder) {
        holder.section.setText("");
        holder.section.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty, 0, 0, 0);
        holder.itemView.setBackgroundResource(R.drawable.navigation_drawer_divider);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = 2;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void highlightSection(SectionViewHolder holder, int position) {
        if (isFirstSectionAndLoggedIn(position)) {
            doHighlightSection(holder);
        } else if (isLastSectionAndLoggedOut(position)) {
            doHighlightSection(holder);
        }
    }

    private boolean isFirstSectionAndLoggedIn(int position) {
        return Auth.isLoggedIn() && position == 0;
    }

    private boolean isLastSectionAndLoggedOut(int position) {
        return !Auth.isLoggedIn() && position == (data.size() - 1);
    }

    private void doHighlightSection(SectionViewHolder holder) {
        holder.section.setTextColor(App.getContext().getResources().getColor(R.color.colorAccent));
        holder.section.setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {

        private TextView section;
        private View itemView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            this.section = (TextView) itemView.findViewById(R.id.section_text);
            this.itemView = itemView;
        }
    }
}
