package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;

import ua.samosfator.moduleok.LoadPageAsyncTask;
import ua.samosfator.moduleok.Preferences;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.Student;

public class SubjectsFragment extends Fragment {

    private Student student;

    public SubjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        student = new Student(getMainPageHtml());
        return rootView;
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
