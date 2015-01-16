package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;

class ModulesPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles = {
            App.getContext().getResources().getString(R.string.module_1_name),
            App.getContext().getResources().getString(R.string.module_2_name),
            App.getContext().getResources().getString(R.string.module_3_name),
            App.getContext().getResources().getString(R.string.module_4_name)
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
        ModuleFragment moduleFragment = new ModuleFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("module", position);

        moduleFragment.setArguments(bundle);

        return moduleFragment;
    }
}
