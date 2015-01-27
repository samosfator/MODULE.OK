package ua.samosfator.moduleok.fragment.navigation_drawer_fragment;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.event.SemesterChangedEvent;

class SemesterSpinner {

    private static Spinner mSemesterSpinner;
    private static DrawerLayout mDrawerLayout;

    private SemesterSpinner() {
    }

    public static void init(DrawerLayout drawerLayout) {
        mSemesterSpinner = (Spinner) drawerLayout.findViewById(R.id.semester_spinner);
        mDrawerLayout = drawerLayout;
        mSemesterSpinner.setAdapter(getAdapter());
        mSemesterSpinner.setSelection(StudentKeeper.getCurrentSemesterIndex());
        initOnItemSelectedListener();
    }

    private static ArrayAdapter<CharSequence> getAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                App.getContext(), R.array.semesters, R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinnet_dropdown_item);
        return adapter;
    }

    private static void initOnItemSelectedListener() {
        mSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                StudentKeeper.setCurrentSemesterIndex(position);
                EventBus.getDefault().post(new SemesterChangedEvent());
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
