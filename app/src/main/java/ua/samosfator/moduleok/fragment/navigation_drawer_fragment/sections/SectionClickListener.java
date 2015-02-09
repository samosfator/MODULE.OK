package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.FragmentUtils;
import ua.samosfator.moduleok.FragmentsKeeper;
import ua.samosfator.moduleok.Preferences;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

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
            case LAST_TOTAL: {
                if (Auth.isLoggedIn()) {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLastTotal());
                } else {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);
                break;
            }
            case MODULES: {
                if (Auth.isLoggedIn()) {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getModules());
                } else {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);
                break;
            }
            case LOG_IN: {
                if (Auth.isLoggedIn()) {
                    Preferences.save("SESSIONID", "");
                    Preferences.save("mainPageHtml", "");

                    FragmentsKeeper.setLogin(new LoginFragment());
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());

                    EventBus.getDefault().post(new LogoutEvent());
                    Mint.logEvent("log out", MintLogLevel.Info);
                } else {
                    FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
                }

                mDrawerLayout.closeDrawers();
                SectionHighlighter.highlightSection(mRecyclerView, view);
                break;
            }
            case EMPTY: {
                break;
            }
            case UPDATE_TIME: {
                Toast.makeText(App.getContext(), App.getContext().getString(R.string.last_synchronized_time), Toast.LENGTH_SHORT).show();
                break;
            }
            case FEEDBACK: {
                Intent openVkGroupIntent = new Intent(Intent.ACTION_VIEW);
                openVkGroupIntent.setData(Uri.parse("https://vk.com/moduleok"));
                openVkGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(openVkGroupIntent);
                break;
            }
            case VERSION: {
                Toast.makeText(App.getContext(), App.getContext().getString(R.string.app_version_hint), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
