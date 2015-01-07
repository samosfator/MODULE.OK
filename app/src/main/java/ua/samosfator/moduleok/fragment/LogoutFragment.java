package ua.samosfator.moduleok.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.samosfator.moduleok.MainActivity;
import ua.samosfator.moduleok.Preferences;
import ua.samosfator.moduleok.R;

public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logout, container, false);
        Preferences.save("SESSIONID", "");
        getActivity().finish();
        startActivity(new Intent(rootView.getContext(), MainActivity.class));
        return rootView;
    }
}
