package ua.samosfator.moduleok.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.samosfator.moduleok.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectItemFragment extends Fragment {


    public SubjectItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_item, container, false);
    }


}
