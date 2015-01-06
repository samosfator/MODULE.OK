package ua.samosfator.moduleok;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Auth {
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.45 Safari/537.36";
    private final String INCORRECT_PASSWORD_MESSAGE = "Не вірний логін/пароль.";

    private boolean success;

    private static Student student;

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

    public static Student getCurrentStudent() {
        if (student == null) {
            initStudent();
        }
        return student;
    }

    private static void initStudent() {
        student = new Student(getMainPageHtml());
    }

    private static String getMainPageHtml() {
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

    private static String loadMainPage() {
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
