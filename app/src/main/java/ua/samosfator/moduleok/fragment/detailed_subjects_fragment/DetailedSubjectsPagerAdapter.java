package ua.samosfator.moduleok.fragment.detailed_subjects_fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.student_bean.Subject;

class DetailedSubjectsPagerAdapter extends FragmentPagerAdapter {

    private final Resources appResources = App.getContext().getResources();
    private List<Subject> mSubjects;

    private final int TYPICAL_MODULES_COUNT = 4;
    private int maxSubjectsCount = TYPICAL_MODULES_COUNT;
    private List<DetailedSubjectFragment> detailedSubjectFragmentCacheList = new ArrayList<>(maxSubjectsCount);

    public DetailedSubjectsPagerAdapter(FragmentManager fm, List<Subject> subjects, int maxSubjectsCount) {
        super(fm);
        this.mSubjects = subjects;
        this.maxSubjectsCount = maxSubjectsCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSubjects.get(position).getName();
    }

    @Override
    public int getCount() {
        return maxSubjectsCount;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= detailedSubjectFragmentCacheList.size()) {
            DetailedSubjectFragment detailedSubjectFragment = new DetailedSubjectFragment();

            Bundle bundle = new Bundle();
            bundle.putString("subjectJson", new Gson().toJson(mSubjects.get(position)));

            detailedSubjectFragment.setArguments(bundle);

            detailedSubjectFragmentCacheList.add(detailedSubjectFragment);

            return detailedSubjectFragment;
        } else {
            return detailedSubjectFragmentCacheList.get(position);
        }
    }
}
