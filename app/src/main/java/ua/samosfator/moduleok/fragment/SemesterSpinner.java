package ua.samosfator.moduleok.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.event.SemesterChangedEvent;

class SemesterSpinner {

    private static Spinner mSemesterSpinner;
    private static ArrayAdapter<CharSequence> semesterSpinnerAdapter;

    private SemesterSpinner() {
    }

    public static void init(View parentLayout) {
        mSemesterSpinner = (Spinner) parentLayout.findViewById(R.id.semester_spinner);
        initAdapter();
        initOnItemSelectedListener();
        mSemesterSpinner.setAdapter(semesterSpinnerAdapter);
    }

    private static void initAdapter() {
        semesterSpinnerAdapter = ArrayAdapter.createFromResource(
                App.getContext(), R.array.semesters, R.layout.spinner_item
        );
    }

    private static void initOnItemSelectedListener() {
        mSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setItemColorLight(parent);
                StudentKeeper.setCurrentSemesterIndex(position);
                EventBus.getDefault().post(new SemesterChangedEvent());
            }

            private void setItemColorLight(AdapterView<?> parent) {
                ((TextView) parent.getChildAt(0)).setTextColor(App.getContext().getResources().getColor(R.color.textColorPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
