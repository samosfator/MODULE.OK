package ua.samosfator.moduleok;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ua.samosfator.moduleok.NavigationDrawerFragment;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.SubjectsFragment;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        setAccountInfo();

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle(R.string.main_toolbar_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setup(R.id.navigation_drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    private void setAccountInfo() {
        TextView studentName_TextView = (TextView) findViewById(R.id.student_name_txt);
        TextView studentGroup_TextView = (TextView) findViewById(R.id.student_group_txt);
        if (Preferences.read("SESSIONID", "").equals("")) {
            studentName_TextView.setText("Іван Іванов");
            studentGroup_TextView.setText("АБВ-23");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new LoginFragment())
                    .commit();
        } else {
            studentName_TextView.setText(Auth.getCurrentStudent().getNameSurname());
            studentGroup_TextView.setText(Auth.getCurrentStudent().getGroupName());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new SubjectsFragment())
                    .commit();
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
                Toast.makeText(this, "Implement refreshing!", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
