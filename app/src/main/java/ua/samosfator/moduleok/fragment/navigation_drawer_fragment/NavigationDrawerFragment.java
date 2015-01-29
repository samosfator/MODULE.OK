package ua.samosfator.moduleok.fragment.navigation_drawer_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.fragment.navigation_drawer_fragment.sections.SectionsRecyclerView;

public class NavigationDrawerFragment extends Fragment {

    private View mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        return mLayout;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        View containerView = getActivity().findViewById(fragmentId);
        SemesterSpinner.init(drawerLayout);
        new SectionsRecyclerView(mLayout, getActivity()).init(drawerLayout);
        new NavigationDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close, containerView);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
