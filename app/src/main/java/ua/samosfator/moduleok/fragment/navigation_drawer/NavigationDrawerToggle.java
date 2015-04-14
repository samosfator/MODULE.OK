package ua.samosfator.moduleok.fragment.navigation_drawer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.MainActivity;
import ua.samosfator.moduleok.Preferences;
import ua.samosfator.moduleok.R;

public class NavigationDrawerToggle extends ActionBarDrawerToggle {

    public static final String KEY_USER_SAW_DRAWER = "user_saw_drawer";
    private String previousToolbarTitle;
    private boolean mUserSawDrawer;
    private FragmentActivity activity;

    public NavigationDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes, View containerView) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = (FragmentActivity) activity;
        drawerLayout.setDrawerListener(this);
        drawerLayout.post(this::syncState);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        previousToolbarTitle = MainActivity.getAppToolbarTitle();
        MainActivity.setAppToolbarTitle(App.getContext().getString(R.string.app_name));
        if (!mUserSawDrawer) {
            saveUserSawDrawerState();
        }
        if (App.isAndroidNewerIceCreamSandwich()) {
            activity.invalidateOptionsMenu();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (MainActivity.getAppToolbarTitle().equals(App.getContext().getString(R.string.app_name))) {
            MainActivity.setAppToolbarTitle(previousToolbarTitle);
        }
        if (App.isAndroidNewerIceCreamSandwich()) {
            activity.invalidateOptionsMenu();
        }
    }

    private void saveUserSawDrawerState() {
        mUserSawDrawer = true;
        Preferences.save(KEY_USER_SAW_DRAWER, String.valueOf(true));
    }
}
