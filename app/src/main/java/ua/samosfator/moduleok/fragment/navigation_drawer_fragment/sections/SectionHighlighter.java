package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;

public class SectionHighlighter {

    private final static Resources appResources = App.getContext().getResources();
    private final static int textColorPrimaryDark = appResources.getColor(R.color.textColorPrimaryDark);
    private final static int colorGrey200 = appResources.getColor(R.color.grey_200);

    public static void highlightSection(RecyclerView sectionRecyclerView, View sectionToHighlight) {
        removeHighlightFromSections(sectionRecyclerView);
        highlightCurrentSection(sectionToHighlight);
    }

    private static void highlightCurrentSection(View sectionToHighlight) {
        sectionToHighlight = ((FrameLayout) sectionToHighlight).getChildAt(0);
        TextView sectionTextView = sectionToHighlight instanceof MaterialRippleLayout ?
                ((TextView) ((MaterialRippleLayout) sectionToHighlight).getChildAt(0)) : (TextView) sectionToHighlight;
        sectionTextView.setTextColor(App.getContext().getResources().getColor(R.color.colorAccent));
        sectionTextView.setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
    }

    private static void removeHighlightFromSections(RecyclerView recyclerView) {
        final int mRecyclerViewChildCount = recyclerView.getChildCount();

        for (int i = 0; i < mRecyclerViewChildCount; i++) {
            if (i != SectionsEnum.EMPTY.INDEX) {
                View recyclerViewChild = ((FrameLayout) recyclerView.getChildAt(i)).getChildAt(0);
                TextView otherSectionTextView = recyclerViewChild instanceof MaterialRippleLayout ?
                        ((TextView) (((MaterialRippleLayout) recyclerViewChild).getChildAt(0))) : (TextView) recyclerViewChild;
                otherSectionTextView.setTextColor(textColorPrimaryDark);
                otherSectionTextView.setBackgroundColor(colorGrey200);
            }
        }
    }
}
