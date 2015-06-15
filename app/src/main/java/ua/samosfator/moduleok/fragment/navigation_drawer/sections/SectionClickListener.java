package ua.samosfator.moduleok.fragment.navigation_drawer.sections;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.utils.Analytics;
import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.Utils;
import ua.samosfator.moduleok.utils.FragmentUtils;
import ua.samosfator.moduleok.utils.FragmentsKeeper;
import ua.samosfator.moduleok.MainActivity;
import ua.samosfator.moduleok.utils.Preferences;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.subjects.DetailedSubjectsFragment;
import ua.samosfator.moduleok.fragment.home.HomeFragment;
import ua.samosfator.moduleok.fragment.modules.ModulesFragment;

public class SectionClickListener implements RecyclerItemClickListener.OnItemClickListener {

    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;

    public SectionClickListener(FragmentActivity activity, DrawerLayout drawerLayout, RecyclerView recyclerView) {
        fragmentManager = activity.getSupportFragmentManager();
        mDrawerLayout = drawerLayout;
        mRecyclerView = recyclerView;
    }

    @SuppressLint("CommitTransaction")
    @Override
    public void onItemClick(View view, int position) {
        SectionsEnum clickedSection = SectionsEnum.getSectionById(position);
        switch (clickedSection) {
            case ALL_SCORES: {
                if (App.isLoggedIn()) {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getHome());
                } else {
                    Log.d("Sections", "Not logged in");
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);

                Analytics.trackFragmentView("Last & Total");
                MainActivity.setAppToolbarTitle(Utils.getSectionNameFor(HomeFragment.class));
                break;
            }
            case MODULES: {
                if (App.isLoggedIn()) {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getModules());
                } else {
                    Log.d("Sections", "Not logged in");
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);

                Analytics.trackFragmentView("Modules");
                MainActivity.setAppToolbarTitle(Utils.getSectionNameFor(ModulesFragment.class));
                break;
            }
            case DETAILED_SUBJECTS: {
                if (App.isLoggedIn()) {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getDetailedSubjectsFragment());
                } else {
                    Log.d("Sections", "Not logged in");
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);

                Analytics.trackFragmentView("Detailed");
                MainActivity.setAppToolbarTitle(Utils.getSectionNameFor(DetailedSubjectsFragment.class));
                break;
            }
            case LOG_IN: {
                if (App.isLoggedIn()) {
                    Preferences.save("login", "");
                    Preferences.save("password", "");
                    Preferences.save("json", "");

                    FragmentsKeeper.setLogin(new LoginFragment());
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());

                    EventBus.getDefault().post(new LogoutEvent());
                    Mint.logEvent("log out", MintLogLevel.Info);
                    App.setIsLoggedIn(false);

                    Analytics.trackFragmentView("Log out");
                } else {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                    Analytics.trackFragmentView("Log in");
                    MainActivity.setAppToolbarTitle(Utils.getSectionNameFor(LoginFragment.class));
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);
                break;
            }
            case EMPTY: {
                break;
            }
            case FEEDBACK: {
                Intent openVkGroupIntent = new Intent(Intent.ACTION_VIEW);
                openVkGroupIntent.setData(Uri.parse("https://vk.com/moduleok"));
                openVkGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(openVkGroupIntent);

                Analytics.trackEvent("Click", "Feedback");
                break;
            }
            case VERSION: {
                Toast.makeText(App.getContext(), App.getContext().getString(R.string.app_version_hint), Toast.LENGTH_SHORT).show();
                Analytics.trackEvent("Click", "App version");
                break;
            }
        }
    }
}
