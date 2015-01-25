package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.Preferences;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.fragment.last_total_fragment.LastTotalFragment;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;
import ua.samosfator.moduleok.recyclerview.DrawerSection;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

public class NavigationDrawerFragment extends Fragment {

    public static final String KEY_USER_SAW_DRAWER = "user_saw_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static List<DrawerSection> mSections;

    private SectionAdapter mSectionAdapter;

    private boolean mUserSawDrawer;
    private boolean mFromSavedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDrawerState(savedInstanceState);
        initSections();
    }

    @Override
    public void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    private void initDrawerState(Bundle savedInstanceState) {
        mUserSawDrawer = Boolean.valueOf(Preferences.read(KEY_USER_SAW_DRAWER, "false"));
        if (savedInstanceState == null) {
            mFromSavedInstanceState = true;
        }
    }

    private void initSections() {
        mSections = new ArrayList<>();

        DrawerSection subjectsSection = new DrawerSection(getString(R.string.last_n_total_section), R.drawable.ic_format_list_numbers_grey600_24dp);
        subjectsSection.setFragment(new LastTotalFragment());
        mSections.add(subjectsSection);

        DrawerSection modulesSection = new DrawerSection(getString(R.string.modules_section), R.drawable.ic_file_document_box_grey600_24dp);
        modulesSection.setFragment(new ModulesFragment());
        mSections.add(modulesSection);

//        DrawerSection statsSection = new DrawerSection("Stats", R.drawable.ic_poll_grey600_24dp);
//        mSections.add(statsSection);

        addLoginOrLogoutSection();
    }

    private void addLoginOrLogoutSection() {
        if (Auth.isLoggedIn()) {
            addLogoutSection();
        } else {
            addLoginSection();
        }
    }

    private void addLogoutSection() {
        DrawerSection logoutSection = new DrawerSection(getString(R.string.logout_section), R.drawable.ic_logout_grey600_24dp);
        logoutSection.setFragment(new LogoutFragment());
        if (mSections.size() == 2) {
            mSections.add(logoutSection);
        } else {
            mSections.set(2, logoutSection);
            mSectionAdapter.notifyItemChanged(2);
        }
    }

    public void addLoginSection() {
        DrawerSection loginSection = new DrawerSection(getString(R.string.login_section), R.drawable.ic_login_grey600_24dp);
        loginSection.setFragment(new LoginFragment());
        if (mSections.size() == 2) {
            mSections.add(loginSection);
        } else {
            mSections.set(2, loginSection);
            mSectionAdapter.notifyItemChanged(2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        initSectionAdapter();
        initRecyclerView(layout);
        return layout;
    }

    private void initSectionAdapter() {
        mSectionAdapter = new SectionAdapter(getActivity(), mSections);
    }

    private void initRecyclerView(View layout) {
        if (mSectionAdapter == null) initSectionAdapter();
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        recyclerView.setAdapter(mSectionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container, mSections.get(position).getFragment())
                        .commit();
                mDrawerLayout.closeDrawers();
            }
        }));
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        View containerView = getActivity().findViewById(R.id.navigation_drawer_fragment);
        mDrawerLayout = drawerLayout;

        SemesterSpinner.init(mDrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserSawDrawer) {
                    saveUserSawDrawerState();
                }
                getActivity().invalidateOptionsMenu();
            }

            private void saveUserSawDrawerState() {
                mUserSawDrawer = true;
                Preferences.save(KEY_USER_SAW_DRAWER, String.valueOf(true));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserSawDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LoginEvent event) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addLoginOrLogoutSection();
            }
        });
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LogoutEvent event) {
        addLoginOrLogoutSection();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
