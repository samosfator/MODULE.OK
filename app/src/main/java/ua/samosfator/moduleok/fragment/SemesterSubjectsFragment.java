package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.SemestersPagerAdapter;

public class SemesterSubjectsFragment extends Fragment {


    public SemesterSubjectsFragment() {
        // Required empty public constructor
        Log.d("SemesterSubjectsFragment Constructor", "SemesterSubjectsFragment Constructor");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_semester_subjects, container, false);

        initTabStrip(rootView);

        return rootView;
    }

    private void initTabStrip(View rootView) {
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.subjects_viewpager);
        pager.setAdapter(new SemestersPagerAdapter(getChildFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }
}
