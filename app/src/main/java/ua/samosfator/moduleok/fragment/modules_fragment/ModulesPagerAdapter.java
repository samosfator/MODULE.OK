package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.samosfator.moduleok.fragment.semesters_subjects_fragment.SubjectsFragment;

class ModulesPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles = {
            "MODULE 1",
            "MODULE 2",
            "MODULE 3",
            "MODULE 4"
    };

    public ModulesPagerAdapter(FragmentManager fm) {
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
