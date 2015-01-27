package ua.samosfator.moduleok.fragment.navigation_drawer_fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.event.LoginEvent;
import ua.samosfator.moduleok.event.LogoutEvent;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.fragment.LogoutFragment;
import ua.samosfator.moduleok.fragment.last_total_fragment.LastTotalFragment;
import ua.samosfator.moduleok.fragment.modules_fragment.ModulesFragment;
import ua.samosfator.moduleok.recyclerview.DrawerSection;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

public class SectionsRecyclerView {

    private List<DrawerSection> mSections;
    private SectionAdapter mSectionAdapter;
    private RecyclerView mRecyclerView;

    private View mParentLayout;
    private FragmentActivity mFragmentActivity;

    public SectionsRecyclerView(View layout, FragmentActivity activity) {
        App.registerClassForEventBus(this);
        mParentLayout = layout;
        mFragmentActivity = activity;
    }

    public void init(DrawerLayout drawerLayout) {
        if (mSectionAdapter == null) initSectionAdapter(mFragmentActivity);
        mRecyclerView = (RecyclerView) mParentLayout.findViewById(R.id.drawer_list);
        mRecyclerView.setAdapter(mSectionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragmentActivity));
        initOnClickListener(drawerLayout);
    }

    private void initSectionAdapter(FragmentActivity activity) {
        initSections();
        mSectionAdapter = new SectionAdapter(activity, mSections);
    }

    private void initOnClickListener(DrawerLayout drawerLayout) {
        SectionClickListener sectionClickListener = new SectionClickListener(mFragmentActivity, drawerLayout, mSections, mRecyclerView);
        RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(mFragmentActivity, sectionClickListener);
        mRecyclerView.addOnItemTouchListener(recyclerItemClickListener);
    }

    private void initSections() {
        mSections = new ArrayList<>();

        DrawerSection subjectsSection = new DrawerSection(App.getContext().getString(R.string.last_n_total_section), R.drawable.ic_format_list_numbers_grey600_24dp);
        subjectsSection.setFragment(new LastTotalFragment());
        mSections.add(subjectsSection);

        DrawerSection modulesSection = new DrawerSection(App.getContext().getString(R.string.modules_section), R.drawable.ic_file_document_box_grey600_24dp);
        modulesSection.setFragment(new ModulesFragment());
        mSections.add(modulesSection);

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
        DrawerSection logoutSection = new DrawerSection(App.getContext().getString(R.string.logout_section), R.drawable.ic_logout_grey600_24dp);
        logoutSection.setFragment(new LogoutFragment());
        if (mSections.size() == 2) {
            mSections.add(logoutSection);
        } else {
            mSections.set(2, logoutSection);
            mSectionAdapter.notifyItemChanged(2);
        }
    }

    public void addLoginSection() {
        DrawerSection loginSection = new DrawerSection(App.getContext().getString(R.string.login_section), R.drawable.ic_login_grey600_24dp);
        loginSection.setFragment(new LoginFragment());
        if (mSections.size() == 2) {
            mSections.add(loginSection);
        } else {
            mSections.set(2, loginSection);
            mSectionAdapter.notifyItemChanged(2);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LoginEvent event) {
        mFragmentActivity.runOnUiThread(new Runnable() {
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
}
