package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.FragmentUtils;
import ua.samosfator.moduleok.FragmentsKeeper;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.event.RefreshEndEvent;
import ua.samosfator.moduleok.student_bean.Semester;
import ua.samosfator.moduleok.student_bean.Subject;

public class ModulesFragment extends Fragment {

    private static FragmentManager fragmentManager;
    private static int maxModulesCount;
    static List<Subject> mSubjects = new ArrayList<>();
    private View rootView;
    private ModulesPagerAdapter modulesPagerAdapter;


    public ModulesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        App.registerClassForEventBus(this);
        Mint.logEvent("view ModulesFragment", MintLogLevel.Info);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_modules, container, false);
        fragmentManager = getFragmentManager();

        initSubjects();
        initTabStrip(rootView);

        return rootView;
    }

    private void initTabStrip(View rootView) {
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.modules_viewpager);
        modulesPagerAdapter = new ModulesPagerAdapter(getChildFragmentManager(), maxModulesCount);
        pager.setAdapter(modulesPagerAdapter);
        pager.setOffscreenPageLimit(maxModulesCount - 1);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.modules_tabs);
        tabs.setViewPager(pager);
    }

    static void initSubjects() {
        try {
            int semesterIndex = StudentKeeper.getCurrentSemesterIndex();
            Semester semester = StudentKeeper.getStudent().getSemester(semesterIndex);
            maxModulesCount = semester.getMaxModuleCount();
            mSubjects.clear();
            mSubjects.addAll(semester.getSubjects());
        } catch (Exception e) {
            e.printStackTrace();
            openLoginFragment();
        }
    }

    private static void openLoginFragment() {
        FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getLogin());
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(RefreshEndEvent event) {
        Log.d("EVENTS-Modules", "RefreshEndEvent");
        if (FragmentsKeeper.getModules().isVisible()) {
            FragmentsKeeper.setModules(new ModulesFragment());
            FragmentUtils.showFragment(fragmentManager.beginTransaction(), FragmentsKeeper.getModules());
        } else {
            FragmentsKeeper.setModules(new ModulesFragment());
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
