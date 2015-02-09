package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.dd.CircularProgressButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.App;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.event.LoginEvent;

public class LoginFragment extends Fragment {
    private CircularProgressButton login_button;
    private MaterialEditText login_txt;
    private MaterialEditText password_txt;

    private View rootView;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("login", login_txt.getText().toString());
        outState.putString("pass", password_txt.getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        password_txt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateAndStartLogin();
                return true;
            }
            return false;
        });
        login_button.setOnClickListener(v -> validateAndStartLogin());

        return rootView;
    }

    private void validateAndStartLogin() {
        String login = login_txt.getText().toString();
        String password = password_txt.getText().toString();

        validateFields(login, password);

        if (isReadyForLogin(login, password)) {
            doLogin(login, password);
        }
    }

    private boolean isReadyForLogin(String login, String password) {
        return !login.isEmpty() && !password.isEmpty() && App.hasInternetConnection();
    }

    private void validateFields(String login, String password) {
        if (login.isEmpty()) {
            login_txt.setError(getString(R.string.enter_login_message));
        }
        if (password.isEmpty()) {
            password_txt.setError(getString(R.string.enter_password_message));
        }
        if (!App.hasInternetConnection()) {
            showInternetConnectionError();
        }
    }

    private void doLogin(final String login, final String password) {
        enableInputs(false);
        Mint.logEvent("log in", MintLogLevel.Info);

        new Thread(() -> {
            Auth auth = new Auth();
            try {
                auth.signIn(login, password);
            } catch (UnknownHostException exception) {
                showUnresolvedHostError();
            }

            if (auth.isSuccess()) {
                Log.d("AUTH_STATUS", String.valueOf(auth.isSuccess()));
                EventBus.getDefault().post(new LoginEvent());
            } else {
                showCredentialsError();
                enableInputs(true);
            }
        }).start();
    }

    private void initViews() {
        login_txt = (MaterialEditText) rootView.findViewById(R.id.login_editText);
        password_txt = (MaterialEditText) rootView.findViewById(R.id.password_editText);
        login_button = (CircularProgressButton) rootView.findViewById(R.id.btnWithText);
    }

    public LoginFragment restoreView() {
        return new LoginFragment();
    }

    private void showInternetConnectionError() {
        login_txt.setError(" ");
        password_txt.setError(getString(R.string.no_internet_connection_text));
    }

    private void showCredentialsError() {
        login_txt.setError(" ");
        password_txt.setError(getString(R.string.wrong_credentials_text));
    }

    private void showUnresolvedHostError() {
        login_txt.setError(" ");
        password_txt.setError(getString(R.string.unresolved_host_error_text));
    }

    private void enableInputs(final boolean bool) {
        new Handler(Looper.getMainLooper()).post(() -> {
            login_txt.setEnabled(bool);
            password_txt.setEnabled(bool);
            login_button.setIndeterminateProgressMode(!bool);
            login_button.setProgress(bool ? 0 : 50);
        });
    }
}
