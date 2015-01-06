package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.NavigationDrawerFragment;
import ua.samosfator.moduleok.R;


public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        final MaterialEditText login_txt = (MaterialEditText) rootView.findViewById(R.id.login_editText);
        final MaterialEditText password_txt = (MaterialEditText) rootView.findViewById(R.id.password_editText);
        final Button login_button = (Button) rootView.findViewById(R.id.login_btn);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_txt.setEnabled(false);
                password_txt.setEnabled(false);
                login_button.setEnabled(false);

                final String login = login_txt.getText().toString();
                final String password = password_txt.getText().toString();

                final Auth auth = new Auth();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        auth.signIn(login, password);

                        if (auth.isSuccess()) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.main_container, NavigationDrawerFragment.mSections.get(0).getFragment())
                                            .commit();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        return rootView;
    }


}
