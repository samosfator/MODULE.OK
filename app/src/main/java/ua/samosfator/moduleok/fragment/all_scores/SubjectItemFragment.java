package ua.samosfator.moduleok.fragment.all_scores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.samosfator.moduleok.R;

public class SubjectItemFragment extends Fragment {

    public SubjectItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject_item, container, false);
    }
}
