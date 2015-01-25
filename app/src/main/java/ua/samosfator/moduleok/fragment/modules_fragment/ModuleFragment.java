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
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;

public class ModuleFragment extends Fragment {

    private ModuleSubjectItemAdapter moduleSubjectItemAdapter;

    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        App.registerClassForEventBus(this);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_module, container, false);
        initModuleSubjectItemAdapter();
        initRecyclerView(rootView);
        return rootView;
    }

    private void initModuleSubjectItemAdapter() {
        moduleSubjectItemAdapter = new ModuleSubjectItemAdapter(getActivity(), ModulesFragment.mSubjects, getArguments().getInt("module"));
    }

    private void initRecyclerView(View rootView) {
        if (moduleSubjectItemAdapter == null) initModuleSubjectItemAdapter();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.modules_subjects_recycler_view);
        recyclerView.setAdapter(moduleSubjectItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(SemesterChangedEvent event) {
        Log.d("SEMESTER_CHANGED_EVENT", "SemesterIndex:" + StudentKeeper.getCurrentSemesterIndex());

        ModulesFragment.initSubjects();
        reRenderModuleSubjectsList();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(RefreshEvent event) {
        StudentKeeper.refreshStudent();
        ModulesFragment.initSubjects();
        reRenderModuleSubjectsList();
    }

    private void reRenderModuleSubjectsList() {
        moduleSubjectItemAdapter.notifyItemRangeChanged(0, moduleSubjectItemAdapter.getItemCount());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
