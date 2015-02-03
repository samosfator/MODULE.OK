package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

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
    private RecyclerView mRecyclerView;

    public SectionClickListener(FragmentActivity activity, DrawerLayout drawerLayout, RecyclerView recyclerView) {
        mFragmentActivity = activity;
        mDrawerLayout = drawerLayout;
        mRecyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position) {
        openSelectedSectionFragment(view, position);
    }

    private void openSelectedSectionFragment(View view, int position) {
        SectionsEnum clickedSection = SectionsEnum.getSectionById(position);
        switch (clickedSection) {
            //Last & Total
            case LAST_TOTAL: {
                mFragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new LastTotalFragment())
                        .commit();
                mDrawerLayout.closeDrawers();
                removeHighlightFromSections();
                highlightSelectedSection(view);
                break;
            }
            //Modules
            case MODULES: {
                mFragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new ModulesFragment())
                        .commit();
                mDrawerLayout.closeDrawers();
                removeHighlightFromSections();
                highlightSelectedSection(view);
                break;
            }
            //Log in / Log out
            case LOG_IN: {
                if (Auth.isLoggedIn()) {
                    mFragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new LogoutFragment())
                            .commit();
                } else {
                    mFragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new LoginFragment())
                            .commit();
                }
                mDrawerLayout.closeDrawers();
                removeHighlightFromSections();
                highlightSelectedSection(view);
                break;
            }
            //Empty
            case EMPTY: {
                break;
            }
            //Update time
            case UPDATE_TIME: {
                Toast.makeText(App.getContext(), App.getContext().getString(R.string.last_synchronized_time), Toast.LENGTH_LONG).show();
                break;
            }
            //Feedback
            case FEEDBACK: {
                Intent openVkGroupIntent = new Intent(Intent.ACTION_VIEW);
                openVkGroupIntent.setData(Uri.parse("https://vk.com/moduleok"));
                openVkGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(openVkGroupIntent);
                break;
            }
            //App version
            case VERSION: {
                Toast.makeText(App.getContext(), App.getContext().getString(R.string.app_version_hint), Toast.LENGTH_SHORT).show();
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
