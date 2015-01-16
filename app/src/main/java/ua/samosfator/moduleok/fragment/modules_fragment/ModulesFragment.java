package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.animation.CircularRevealAnimation;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;

public class ModulesFragment extends Fragment {

    public ModulesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        CircularRevealAnimation.addForView(getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modules, container, false);

        initTabStrip(rootView);

        return rootView;
    }

    private void initTabStrip(View rootView) {
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.modules_viewpager);
        pager.setAdapter(new ModulesPagerAdapter(getChildFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.modules_tabs);
        tabs.setViewPager(pager);
    }
}