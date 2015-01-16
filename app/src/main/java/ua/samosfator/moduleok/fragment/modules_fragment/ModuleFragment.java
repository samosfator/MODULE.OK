package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.animation.CircularRevealAnimation;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.parser.Subject;
import ua.samosfator.moduleok.recyclerview.adapter.ModuleSubjectItemAdapter;

public class ModuleFragment extends Fragment {

    private List<Subject> mSubjects = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ModuleSubjectItemAdapter moduleSubjectItemAdapter;

    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_module, container, false);

        initSubjects();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.modules_subjects_recycler_view);
        moduleSubjectItemAdapter = new ModuleSubjectItemAdapter(getActivity(), mSubjects, getArguments().getInt("module"));
        mRecyclerView.setAdapter(moduleSubjectItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void initSubjects() {
        try {
            int semester = StudentKeeper.getCurrentSemesterIndex();
            mSubjects.clear();
            mSubjects.addAll(StudentKeeper.getCurrentStudent().getSemesters().get(semester).getSubjects());
        } catch (IllegalArgumentException e) {
            openLoginFragment();
        }
    }

    private void openLoginFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginFragment())
                .commit();
    }

    public void onEvent(RefreshEvent event) {
        StudentKeeper.refreshStudent();
        initSubjects();
        rerenderModuleSubjectsList();
    }

    public void onEvent(SemesterChangedEvent event) {
        Log.d("[ModuleFragment#onEvent(SemesterChangedEvent)]",
                "Current semesterIndex:" + StudentKeeper.getCurrentSemesterIndex());

        initSubjects();
        CircularRevealAnimation.addForView(mRecyclerView);
        rerenderModuleSubjectsList();
    }

    private void rerenderModuleSubjectsList() {
        moduleSubjectItemAdapter.notifyItemRangeChanged(0, moduleSubjectItemAdapter.getItemCount());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
