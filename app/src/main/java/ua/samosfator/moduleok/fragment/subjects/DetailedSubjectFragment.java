package ua.samosfator.moduleok.fragment.subjects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.student_bean.Module;
import ua.samosfator.moduleok.student_bean.Subject;

public class DetailedSubjectFragment extends Fragment {

    private DetailedSubjectsSubjectItemAdapter detailedSubjectsSubjectItemAdapter;

    public DetailedSubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModuleSubjectItemAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_module, container, false);
        initRecyclerView(rootView);
        return rootView;
    }

    private void initModuleSubjectItemAdapter() {
        String subjectJson = getArguments().getString("subjectJson");
        Subject subject = new Gson().fromJson(subjectJson, new TypeToken<Subject>() {
        }.getType());

        Module totalScoreModule = new Module();
        totalScoreModule.setWeight(0);
        totalScoreModule.setScore(subject.getTotalScore());

        if (subject.isExam()) {
            totalScoreModule.setDate("total");
            subject.getModules().add(totalScoreModule);
        } else {
            totalScoreModule.setDate("total-zalik");
            subject.getModules().add(totalScoreModule);
        }

        detailedSubjectsSubjectItemAdapter = new DetailedSubjectsSubjectItemAdapter(getActivity(), subject);
    }

    private void initRecyclerView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.modules_subjects_recycler_view);
        recyclerView.setAdapter(detailedSubjectsSubjectItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
