package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.LogoutFragment;
import ua.samosfator.moduleok.fragment.last_total_fragment.LastTotalFragment;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

public class SectionClickListener implements RecyclerItemClickListener.OnItemClickListener {

    private FragmentActivity mFragmentActivity;
    private DrawerLayout mDrawerLayout;
    private List<SectionDrawer> mSections;
    private RecyclerView mRecyclerView;

    public SectionClickListener(FragmentActivity activity, DrawerLayout drawerLayout, List<SectionDrawer> sections, RecyclerView recyclerView) {
        mFragmentActivity = activity;
        mDrawerLayout = drawerLayout;
        mSections = sections;
        mRecyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position) {
        openSelectedSectionFragment(position);
        removeHighlightFromSections();
        highlightSelectedSection(view);
        mDrawerLayout.closeDrawers();
    }

    private void openSelectedSectionFragment(int position) {
        switch (position) {
            //Last & Total
            case 0: {
                mFragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new LastTotalFragment())
                        .commit();
                break;
            }
            //Modules
            case 1: {
                mFragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new ModulesFragment())
                        .commit();
                break;
            }
            //Feedback
            case 2: {
                Intent openVkGroupIntent = new Intent(Intent.ACTION_VIEW);
                openVkGroupIntent.setData(Uri.parse("https://vk.com/moduleok"));
                openVkGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(openVkGroupIntent);
                break;
            }
            //Log in / Log out
            case 3: {
                if (Auth.isLoggedIn()) {
                    mFragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new LogoutFragment())
                            .commit();
                } else {
                    mFragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new LoginFragment())
                            .commit();
                }
                break;
            }
        }
    }

    private void highlightSelectedSection(View view) {
        TextView sectionTextView = view instanceof MaterialRippleLayout ?
                ((TextView) ((MaterialRippleLayout) view).getChildAt(0)) : (TextView) view;
        sectionTextView.setTextColor(App.getContext().getResources().getColor(R.color.colorAccent));
        sectionTextView.setBackgroundColor(App.getContext().getResources().getColor(R.color.grey_300));
    }

    private void removeHighlightFromSections() {
        Resources appResources = App.getContext().getResources();
        int textColorPrimaryDark = appResources.getColor(R.color.textColorPrimaryDark);
        int colorGrey200 = appResources.getColor(R.color.grey_200);

        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View recyclerViewChild = mRecyclerView.getChildAt(i);
            TextView otherSectionTextView = recyclerViewChild instanceof MaterialRippleLayout ?
                    ((TextView) ((MaterialRippleLayout) recyclerViewChild).getChildAt(0)) : (TextView) recyclerViewChild;
            otherSectionTextView.setTextColor(textColorPrimaryDark);
            otherSectionTextView.setBackgroundColor(colorGrey200);
        }
    }
}
