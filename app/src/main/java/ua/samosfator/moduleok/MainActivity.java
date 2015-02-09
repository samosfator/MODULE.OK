package ua.samosfator.moduleok;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.event.RefreshEndEvent;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.fragment.last_total_fragment.LastTotalFragment;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;
import ua.samosfator.moduleok.fragment.navigation_drawer_fragment.NavigationDrawerFragment;
import ua.samosfator.moduleok.notification.ScoreCheckerService;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.setProperty("java8.util.Spliterators.assume.oracle.collections.impl", "false");
        Mint.initAndStartSession(MainActivity.this, "79d18253");
        Mint.logEvent("start MainActivity", MintLogLevel.Info);

        setContentView(R.layout.activity_main);
        initAndSetAccountInfo();
        StudentKeeper.initSemesterIndex();

        initToolbar();
        initNavigationDrawer();

        if (Auth.isLoggedIn() && App.hasInternetConnection()) {
            startService(new Intent(this, ScoreCheckerService.class));
        }
    }

    @Override
    protected void onResume() {
        App.registerClassForEventBus(this);
        super.onResume();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle(R.string.main_toolbar_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initNavigationDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setup(R.id.navigation_drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    private void initAndSetAccountInfo() {
        if (!Auth.isLoggedIn()) {
            eraseAccountInfo();
            openLoginFragment();
            return;
        }

        final TextView studentName_TextView = (TextView) findViewById(R.id.student_name_txt);
        final TextView studentGroup_TextView = (TextView) findViewById(R.id.student_group_txt);

        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                studentName_TextView.setText(StudentKeeper.forceInitAndGetCurrentStudent().getNameSurname());
                studentGroup_TextView.setText(StudentKeeper.forceInitAndGetCurrentStudent().getGroupName());

                openLastTotalFragment();
            } catch (SessionIdExpiredException e) {
                eraseAccountInfo();
                openLoginFragment();
            }
        });
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

    @SuppressLint("CommitTransaction")
    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LoginEvent event) {
        EventBus.getDefault().post(new RefreshEvent());
        initAndSetAccountInfo();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LogoutEvent event) {
        eraseAccountInfo();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(RefreshEvent event) {
        StudentKeeper.refreshStudent();
        EventBus.getDefault().post(new RefreshEndEvent());
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
                if (App.hasInternetConnection()) {
                    Toast.makeText(this, getString(R.string.action_refresh_toast), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new RefreshEvent());
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
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
