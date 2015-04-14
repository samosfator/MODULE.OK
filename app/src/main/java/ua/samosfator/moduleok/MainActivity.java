package ua.samosfator.moduleok;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.LoadPageCompleteEvent;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.event.RefreshEndEvent;
import ua.samosfator.moduleok.fragment.navigation_drawer.NavigationDrawerFragment;
import ua.samosfator.moduleok.notification.NewScoresService;
import ua.samosfator.moduleok.rating.FacultyRatingSender;
import ua.samosfator.moduleok.utils.Analytics;
import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.FragmentUtils;
import ua.samosfator.moduleok.utils.FragmentsKeeper;
import ua.samosfator.moduleok.utils.Preferences;
import ua.samosfator.moduleok.utils.StudentKeeper;

public class MainActivity extends ActionBarActivity {

    private static Toolbar toolbar;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(MainActivity.this, "79d18253");
        Mint.logEvent("start MainActivity", MintLogLevel.Info);

        setContentView(R.layout.activity_main);
        tryToRestorePreviousData();
        setAccountInfo();
        StudentKeeper.initSemesterIndex();

        initToolbar();
        initNavigationDrawer();
        new Handler(Looper.getMainLooper()).postDelayed(FacultyRatingSender::sendTotalScoreOnStart, 1500);

        if (!App.isServiceRunning(NewScoresService.class)) {
            startService(new Intent(this, NewScoresService.class));
        }
    }

    @Override
    protected void onResume() {
        App.registerClassForEventBus(this);
        super.onResume();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle(R.string.lastntotal_section);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public static void setAppToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    public static String getAppToolbarTitle() {
        return String.valueOf(toolbar.getTitle());
    }

    private void initNavigationDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setup(R.id.navigation_drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    private void setAccountInfo() {
        if (!App.isLoggedIn()) {
            eraseAccountInfo();
            openLoginFragment();
            return;
        }

        final TextView studentName_TextView = (TextView) findViewById(R.id.student_name_txt);
        final TextView studentGroup_TextView = (TextView) findViewById(R.id.student_group_txt);

        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                studentName_TextView.setText(StudentKeeper.getStudent().getShortName());
                studentGroup_TextView.setText(StudentKeeper.getStudent().getGroup());

                openLastTotalFragment();
            } catch (Exception e) {
                e.printStackTrace();
                eraseAccountInfo();
                Log.d("MAIN", "Opening LoginFragment from setAccountInfo()");
                openLoginFragment();
            }
        });
    }

    private void tryToRestorePreviousData() {
        String json = Preferences.read("json", "");
        if (json.length() > 0) {
            StudentKeeper.initStudentFromBackup(json);
            App.setIsLoggedIn(true);
        }
    }

    private void eraseAccountInfo() {
        final TextView studentName_TextView = (TextView) findViewById(R.id.student_name_txt);
        final TextView studentGroup_TextView = (TextView) findViewById(R.id.student_group_txt);

        runOnUiThread(() -> {
            studentName_TextView.setText(getString(R.string.sampleStudentName));
            studentGroup_TextView.setText(getString(R.string.sampleStudentGroup));
        });
    }

    @SuppressLint("CommitTransaction")
    private void openLastTotalFragment() {
        FragmentUtils.showFragment(getSupportFragmentManager().beginTransaction(), FragmentsKeeper.getLastTotal());
    }

    @SuppressLint("CommitTransaction")
    private void openLoginFragment() {
        FragmentUtils.showFragment(getSupportFragmentManager().beginTransaction(), FragmentsKeeper.getLogin());
    }

    public void onEvent(LoginEvent event) {
        Log.d("EVENTS-Main", "LoginEvent");
        setAccountInfo();
        EventBus.getDefault().post(new RefreshEndEvent());
    }

    public void onEvent(LogoutEvent event) {
        Log.d("EVENTS-Main", "LogoutEvent");
        eraseAccountInfo();
    }

    public void onEvent(LoadPageCompleteEvent event) {
        Log.d("EVENTS-Main", "LoadPageCompleteEvent");
        EventBus.getDefault().post(new RefreshEndEvent());
    }

    public void onEvent(RefreshEndEvent event) {
        Log.d("EVENTS-Main", "RefreshEndEvent");
        if (App.isLoggedIn()) {
            FacultyRatingSender.sendTotalScoreOnRefresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                Analytics.trackEvent("Click", "Refresh");
                Log.d("MAIN", "Clicking Refresh");

                if (!App.isLoggedIn()) break;

                if (App.hasInternetConnection()) {
                    Toast.makeText(this, getString(R.string.action_refresh_toast), Toast.LENGTH_SHORT).show();
                    new Thread(StudentKeeper::initStudentFromRefresh).start();
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection_text), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_to_exit_toast), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
