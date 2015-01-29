package ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections;

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
import ua.samosfator.moduleok.event.UpdateTimeChange;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

public class SectionsRecyclerView {

    private List<SectionDrawer> mSections;
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
        mRecyclerView = (RecyclerView) mParentLayout.findViewById(R.id.sections_list);
        mRecyclerView.setAdapter(mSectionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragmentActivity));
        initOnClickListener(drawerLayout);
    }

    private void initSectionAdapter(FragmentActivity activity) {
        initSections();
        mSectionAdapter = new SectionAdapter(activity, mSections);
        addLoginOrLogoutSection();
    }

    private void initOnClickListener(DrawerLayout drawerLayout) {
        SectionClickListener sectionClickListener = new SectionClickListener(mFragmentActivity, drawerLayout, mRecyclerView);
        RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(mFragmentActivity, sectionClickListener);
        mRecyclerView.addOnItemTouchListener(recyclerItemClickListener);
    }

    private void initSections() {
        mSections = new ArrayList<>();

        SectionDrawer lastTotalSection = new SectionDrawer(App.getContext().getString(R.string.last_n_total_section), R.drawable.ic_format_list_numbers_grey600_24dp);
        mSections.add(lastTotalSection);

        SectionDrawer modulesSection = new SectionDrawer(App.getContext().getString(R.string.modules_section), R.drawable.ic_file_document_box_grey600_24dp);
        mSections.add(modulesSection);

        SectionDrawer updateTimeSection = new SectionDrawer(App.getFormattedUpdateTime(), R.drawable.ic_timer_sand_grey600_24dp);
        mSections.add(updateTimeSection);

        SectionDrawer feedbackSection = new SectionDrawer(App.getContext().getString(R.string.feedback_section), R.drawable.ic_help_circle_grey600_24dp);
        mSections.add(feedbackSection);

        SectionDrawer versionSection = new SectionDrawer("v." + App.getVersion(), R.drawable.ic_information_grey600_24dp);
        mSections.add(versionSection);
    }

    private void addLoginOrLogoutSection() {
        if (Auth.isLoggedIn()) {
            addLogoutSection();
        } else {
            addLoginSection();
        }
    }

    private void addLogoutSection() {
        SectionDrawer logoutSection = new SectionDrawer(App.getContext().getString(R.string.logout_section), R.drawable.ic_logout_grey600_24dp);
        insertSectionToLastPosition(logoutSection);
    }

    public void addLoginSection() {
        SectionDrawer loginSection = new SectionDrawer(App.getContext().getString(R.string.login_section), R.drawable.ic_login_grey600_24dp);
        insertSectionToLastPosition(loginSection);
    }

    private void insertSectionToLastPosition(SectionDrawer section) {
        if (mSections.size() == 5) {
            mSections.add(section);
        } else {
            mSections.set(5, section);
            mSectionAdapter.notifyItemChanged(5);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LoginEvent event) {
        mFragmentActivity.runOnUiThread(this::addLoginOrLogoutSection);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(LogoutEvent event) {
        addLoginOrLogoutSection();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(UpdateTimeChange event) {
        mSections.get(Sections.UPDATE_TIME.INDEX).setText(App.getFormattedUpdateTime());
    }
}