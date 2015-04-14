package ua.samosfator.moduleok.fragment.last_and_total;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.utils.App;
import ua.samosfator.moduleok.utils.FragmentUtils;
import ua.samosfator.moduleok.utils.FragmentsKeeper;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.utils.StudentKeeper;
import ua.samosfator.moduleok.event.RefreshEndEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;
import ua.samosfator.moduleok.fragment.navigation_drawer.sections.RecyclerItemClickListener;
import ua.samosfator.moduleok.student_bean.Semester;
import ua.samosfator.moduleok.student_bean.Subject;

public class LastTotalFragment extends Fragment {

    private List<Subject> mSubjects = new ArrayList<>();
    private int semesterDividerIndex = -100;
    private SubjectItemAdapter mSubjectItemAdapter;

    public LastTotalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        App.registerClassForEventBus(this);
        Mint.logEvent("view LastTotalFragment", MintLogLevel.Info);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_lastntotal, container, false);
        initSubjects();
        initSectionAdapter();
        initRecyclerView(rootView);
        return rootView;
    }

    private void initSectionAdapter() {
        mSubjectItemAdapter = new SubjectItemAdapter(getActivity(), mSubjects, semesterDividerIndex);
    }

    private void initRecyclerView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.subjects_recycler_view);
        recyclerView.setAdapter(mSubjectItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new SubjectItemOnClickListener(mSubjects, getFragmentManager())));
    }

    private void initSubjects() {
        mSubjects.clear();
        int semesterIndex = StudentKeeper.getCurrentSemesterIndex();
        if (semesterIndex == 2) {
            Semester firstSemester = StudentKeeper.getStudent().getSemester(0);
            Semester secondSemester = StudentKeeper.getStudent().getSemester(1);

            List<Subject> allSubjects = new ArrayList<>();

            allSubjects.addAll(firstSemester.getSubjects());
            Subject secondSemesterDivider = new Subject();
            secondSemesterDivider.setName(getString(R.string.second_semester_name));
            allSubjects.add(secondSemesterDivider);

            semesterDividerIndex = allSubjects.size() - 1;

            allSubjects.addAll(secondSemester.getSubjects());

            mSubjects.addAll(allSubjects);
        } else {
            List<Subject> subjects = StudentKeeper.getStudent().getSemester(semesterIndex).getSubjects();
            mSubjects.addAll(subjects);
        }
    }

    private void openLoginFragment() {
        FragmentUtils.showFragment(getFragmentManager().beginTransaction(), FragmentsKeeper.getLogin());
    }

    public void onEvent(RefreshEndEvent event) {
        Log.d("EVENTS-LastTotal", "RefreshEndEvent");
        if (FragmentsKeeper.getLastTotal().isVisible()) {
            FragmentsKeeper.setLastTotal(new LastTotalFragment());
            FragmentUtils.showFragment(getFragmentManager().beginTransaction(), FragmentsKeeper.getLastTotal());
        } else {
            FragmentsKeeper.setLastTotal(new LastTotalFragment());
        }
    }

    public void onEvent(SemesterChangedEvent event) {
        Log.d("SEMESTER_CHANGED_EVENT", "SemesterIndex:" + StudentKeeper.getCurrentSemesterIndex());
        if (FragmentsKeeper.getLastTotal().isVisible()) {
            FragmentsKeeper.setLastTotal(new LastTotalFragment());
            FragmentUtils.showFragment(getFragmentManager().beginTransaction(), FragmentsKeeper.getLastTotal());
        } else {
            FragmentsKeeper.setLastTotal(new LastTotalFragment());
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
