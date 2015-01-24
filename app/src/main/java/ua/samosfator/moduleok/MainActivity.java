package ua.samosfator.moduleok;

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
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.NavigationDrawerFragment;
import ua.samosfator.moduleok.fragment.semesters_subjects_fragment.SubjectsFragment;

public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Mint.initAndStartSession(MainActivity.this, "79d18253");
        Mint.logEvent("start MainActivity", MintLogLevel.Info);
        Preferences.init(getApplicationContext());

        setContentView(R.layout.activity_main);
        setAccountInfo();
        StudentKeeper.initSemesterIndex();

        initToolbar();
        initNavigationDrawer();
    }

    @Override
    protected void onResume() {
        registerClassForEventBus();
        super.onResume();
    }

    private void registerClassForEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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

    private void eraseAccountInfo() {
        final TextView studentName_TextView = (TextView) findViewById(R.id.student_name_txt);
        final TextView studentGroup_TextView = (TextView) findViewById(R.id.student_group_txt);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                studentName_TextView.setText("Іван Іванов");
                studentGroup_TextView.setText("АБВ-23");
                openLoginFragment();
            }
        });
    }

    private void setAccountInfo() {
        if (!Auth.isLoggedIn()) {
            eraseAccountInfo();
            return;
        }

        final TextView studentName_TextView = (TextView) findViewById(R.id.student_name_txt);
        final TextView studentGroup_TextView = (TextView) findViewById(R.id.student_group_txt);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    studentName_TextView.setText(StudentKeeper.getCurrentStudent().getNameSurname());
                    studentGroup_TextView.setText(StudentKeeper.getCurrentStudent().getGroupName());

                    openSubjectsFragment();
                } catch (SessionIdExpiredException e) {
                    eraseAccountInfo();
                    openLoginFragment();
                }
            }
        });
    }

    private void openSubjectsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new SubjectsFragment())
                .commit();
    }

    private void openLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginFragment())
                .commit();
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
                EventBus.getDefault().post(new RefreshEvent());
                Toast.makeText(this, getString(R.string.action_refresh_toast), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(LoginEvent event) {
        setAccountInfo();
    }

    public void onEvent(LogoutEvent event) {
        eraseAccountInfo();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
