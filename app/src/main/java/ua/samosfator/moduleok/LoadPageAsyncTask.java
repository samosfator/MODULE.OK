package ua.samosfator.moduleok;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class LoadPageAsyncTask extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        Document mainPage = Jsoup.parse("<div id=\"content\"></div>");
        try {
            mainPage = Jsoup.connect("http://mod.tanet.edu.te.ua/index.php")
                    .cookie("PHPSESSID", Preferences.read("SESSIONID", "")).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainPage.select("#content").html();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}