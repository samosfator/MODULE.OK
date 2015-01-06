package ua.samosfator.moduleok;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Auth {
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.45 Safari/537.36";
    private final String INCORRECT_PASSWORD_MESSAGE = "Не вірний логін/пароль.";

    private boolean success;

    public void signIn(String login, String password) {
        Connection.Response loginResponse = null;
        try {
            loginResponse = Jsoup.connect("http://mod.tanet.edu.te.ua/site/login")
                    .userAgent(USER_AGENT)
                    .data("LoginForm[login]", login)
                    .data("LoginForm[password]", password)
                    .data("LoginForm[rememberMe]", "1")
                    .data("yt0", "Увійти")
                    .method(Connection.Method.POST)
                    .execute();
            Document loginDocument = loginResponse.parse();
            success = !(loginDocument.text().contains(INCORRECT_PASSWORD_MESSAGE));
            if (success) {
                Preferences.save("SESSIONID", loginResponse.cookie("PHPSESSID"));
                success = true;
            } else {
                Preferences.save("SESSIONID", "");
                success = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
