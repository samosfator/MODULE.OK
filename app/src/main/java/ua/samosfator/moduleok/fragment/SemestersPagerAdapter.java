package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;

public class SemestersPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles = {
            App.getContext().getString(R.string.first_semester_name),
            App.getContext().getString(R.string.second_semester_name)
    };

    public SemestersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        SubjectsFragment subjectsFragment = new SubjectsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("semester", position);

        subjectsFragment.setArguments(bundle);

        return subjectsFragment;
    }
}
