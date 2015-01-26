package ua.samosfator.moduleok;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.LoginEvent;

public class Auth {
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.45 Safari/537.36";
    private final String INCORRECT_PASSWORD_MESSAGE = "Не вірний логін/пароль.";

    private boolean success;

    public void signIn(String login, String password) {
        downloadSessionId(login, password);
    }

    private void downloadSessionId(String login, String password) {
        Connection.Response loginResponse;
        try {
            loginResponse = downloadLoginResponse(login, password);
            Document loginDocument = loginResponse.parse();
            success = getLoginSuccess(loginDocument);
            saveSessionId(loginResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection.Response downloadLoginResponse(String login, String password) throws IOException {
        Connection.Response loginResponse;
        loginResponse = Jsoup.connect("http://mod.tanet.edu.te.ua/site/login")
                .userAgent(USER_AGENT)
                .data("LoginForm[login]", login)
                .data("LoginForm[password]", password)
                .data("LoginForm[rememberMe]", "1")
                .data("yt0", "Увійти")
                .method(Connection.Method.POST)
                .execute();
        return loginResponse;
    }

    private boolean getLoginSuccess(Document loginDocument) {
        return !(loginDocument.text().contains(INCORRECT_PASSWORD_MESSAGE));
    }

    private void saveSessionId(Connection.Response loginResponse) {
        if (success) {
            Preferences.save("SESSIONID", loginResponse.cookie("PHPSESSID"));
        } else {
            Preferences.save("SESSIONID", "");
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public static boolean isLoggedIn() {
        return !Preferences.read("SESSIONID", "").equals("");
    }
}
