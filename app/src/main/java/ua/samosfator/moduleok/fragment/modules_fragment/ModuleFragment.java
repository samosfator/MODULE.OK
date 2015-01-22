package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;

public class ModuleFragment extends Fragment {

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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.modules_subjects_recycler_view);
        moduleSubjectItemAdapter = new ModuleSubjectItemAdapter(getActivity(), ModulesFragment.mSubjects, getArguments().getInt("module"));
        mRecyclerView.setAdapter(moduleSubjectItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    public void onEvent(RefreshEvent event) {
        StudentKeeper.refreshStudent();
        ModulesFragment.initSubjects();
        rerenderModuleSubjectsList();
    }

    public void onEvent(SemesterChangedEvent event) {
        Log.d("[ModuleFragment#onEvent(SemesterChangedEvent)]",
                "Current semesterIndex:" + StudentKeeper.getCurrentSemesterIndex());

        ModulesFragment.initSubjects();
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
