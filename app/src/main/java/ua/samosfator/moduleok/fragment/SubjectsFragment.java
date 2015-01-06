package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ua.samosfator.moduleok.LoadPageAsyncTask;
import ua.samosfator.moduleok.Preferences;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.Student;
import ua.samosfator.moduleok.parser.Subject;
import ua.samosfator.moduleok.recyclerview.adapter.SubjectItemAdapter;

public class SubjectsFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static List<Subject> mSubjects;

    private RecyclerView mRecyclerView;
    private SubjectItemAdapter mSectionAdapter;

    private Student mStudent;

    public SubjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        initStudent();
        setAccountInfo();
        mSubjects = mStudent.getSemesters().getFirst().getSubjects();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.subjects_recycler_view);
        mSectionAdapter = new SubjectItemAdapter(getActivity(), mSubjects);
        mRecyclerView.setAdapter(mSectionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    private void setAccountInfo() {
        TextView studentName_TextView = (TextView) getActivity().findViewById(R.id.student_name_txt);
        TextView studentGroup_TextView = (TextView) getActivity().findViewById(R.id.student_group_txt);

        studentName_TextView.setText(mStudent.getNameSurname());
        studentGroup_TextView.setText(mStudent.getGroupName());
    }

    private void initStudent() {
        mStudent = new Student(getMainPageHtml());
    }

    private String getMainPageHtml() {
        final String savedMainPageHtml = Preferences.read("mainPageHtml", "");
        String mainPageHtml;
        if (savedMainPageHtml.length() < 10) {
            mainPageHtml = loadMainPage();
            Preferences.save("mainPageHtml", mainPageHtml);
        } else {
            mainPageHtml = savedMainPageHtml;
        }
        return mainPageHtml;
    }

    private String loadMainPage() {
        LoadPageAsyncTask loadPageAsyncTask = new LoadPageAsyncTask();
        loadPageAsyncTask.execute();
        try {
            return loadPageAsyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }
}
