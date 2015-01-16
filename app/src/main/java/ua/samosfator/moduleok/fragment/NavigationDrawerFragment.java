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
import ua.samosfator.moduleok.animation.CircularRevealAnimation;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;
import ua.samosfator.moduleok.fragment.semesters_subjects_fragment.SubjectsFragment;
import ua.samosfator.moduleok.recyclerview.DrawerSection;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;
import ua.samosfator.moduleok.recyclerview.adapter.SectionAdapter;

public class NavigationDrawerFragment extends Fragment {

    public static final String KEY_USER_SAW_DRAWER = "user_saw_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static List<DrawerSection> mSections;

    private RecyclerView mRecyclerView;
    private SectionAdapter mSectionAdapter;

    private boolean mUserSawDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;


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

        DrawerSection subjectsSection = new DrawerSection("Subjects", R.drawable.ic_format_list_numbers_grey600_24dp);
        subjectsSection.setFragment(new SubjectsFragment());
        mSections.add(subjectsSection);

        DrawerSection modulesSection = new DrawerSection("Modules", R.drawable.ic_file_document_box_grey600_24dp);
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
        DrawerSection logoutSection = new DrawerSection("Log out", R.drawable.ic_logout_grey600_24dp);
        logoutSection.setFragment(new LogoutFragment());
        if (mSections.size() == 2) {
            mSections.add(logoutSection);
        } else {
            mSections.set(2, logoutSection);
            mSectionAdapter.notifyItemChanged(2);
        }
    }

    public void addLoginSection() {
        DrawerSection loginSection = new DrawerSection("Log in", R.drawable.ic_login_grey600_24dp);
        loginSection.setFragment(new LoginFragment());
        if (mSections.size() == 2) {
            mSections.add(loginSection);
        } else {
            mSections.set(2, loginSection);
            mSectionAdapter.notifyItemChanged(2);
        }
    }

    public void onEvent(LoginEvent event) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addLoginOrLogoutSection();
            }
        });
    }

    public void onEvent(LogoutEvent event) {
        addLoginOrLogoutSection();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        mSectionAdapter = new SectionAdapter(getActivity(), mSections);
        mRecyclerView.setAdapter(mSectionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container, mSections.get(position).getFragment())
                        .commit();
                mDrawerLayout.closeDrawers();
            }
        }));
        return layout;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
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
                Preferences.save(KEY_USER_SAW_DRAWER, String.valueOf(mUserSawDrawer));
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

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
