package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.animation.CircularRevealAnimation;
import ua.samosfator.moduleok.event.LoginEvent;

public class LoginFragment extends Fragment {

    private MaterialEditText login_txt;
    private MaterialEditText password_txt;
    private Button login_button;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(rootView);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableInputs(false);

                final String login = login_txt.getText().toString();
                final String password = password_txt.getText().toString();

                doLogin(login, password);
            }
        });

        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                CircularRevealAnimation.addForView(v);
            }
        });

        return rootView;
    }

    private void doLogin(final String login, final String password) {
        final Auth auth = new Auth();

        new Thread(new Runnable() {
            @Override
            public void run() {
                auth.signIn(login, password);

                if (auth.isSuccess()) {

                    Log.d("LoginFragment#doLogin->auth.isSuccess()", String.valueOf(auth.isSuccess()));

                    EventBus.getDefault().post(new LoginEvent());
                } else {
                    showError();
                    enableInputs(true);
                }
            }
        }).start();
    }

    private void initViews(View rootView) {
        login_txt = (MaterialEditText) rootView.findViewById(R.id.login_editText);
        password_txt = (MaterialEditText) rootView.findViewById(R.id.password_editText);
        login_button = (Button) rootView.findViewById(R.id.login_btn);
    }

    private void showError() {
        password_txt.setError(getString(R.string.wrong_credentials_text));
    }

    private void enableInputs(final boolean bool) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                login_txt.setEnabled(bool);
                password_txt.setEnabled(bool);
                login_button.setEnabled(bool);
            }
        });
    }
}
