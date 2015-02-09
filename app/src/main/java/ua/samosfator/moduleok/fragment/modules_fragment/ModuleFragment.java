package ua.samosfator.moduleok.fragment.modules_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.event.RefreshEndEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;

public class ModuleFragment extends Fragment {

    private ModuleSubjectItemAdapter moduleSubjectItemAdapter;
    private Map<Integer, RecyclerView> cacheRecyclerView = new HashMap<>();

    public ModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        App.registerClassForEventBus(this);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModuleSubjectItemAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_module, container, false);
        if (cacheRecyclerView.get(getArguments().getInt("module")) == null) {
            initRecyclerView(rootView);
        } else {
            cacheRecyclerView.get(getArguments().getInt("module"));
        }
        return rootView;
    }

    private void initModuleSubjectItemAdapter() {
        moduleSubjectItemAdapter = new ModuleSubjectItemAdapter(getActivity(),
                ModulesFragment.mSubjects, getArguments().getInt("module"));
    }

    private void initRecyclerView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.modules_subjects_recycler_view);
        recyclerView.setAdapter(moduleSubjectItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cacheRecyclerView.put(getArguments().getInt("module"), recyclerView);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(SemesterChangedEvent event) {
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
