package ua.samosfator.moduleok;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.RefreshEndEvent;

class LoadPageAsyncTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
        Document mainPage = Jsoup.parse("<div id=\"content\"></div>");
        try {
//            mainPage = getFromCustomRemoteSource();
            mainPage = getFromRemoteSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        postRefreshEndEvent();
        return mainPage.html();
    }

    private void postRefreshEndEvent() {
        new Handler(Looper.getMainLooper()).post(() -> EventBus.getDefault().post(new RefreshEndEvent()));
    }

    private Document getFromRemoteSource() throws IOException {
        return Jsoup.connect("http://mod.tanet.edu.te.ua/ratings/index")
                .cookie("PHPSESSID", Preferences.read("SESSIONID", "")).get();
    }

    private Document getFromCustomRemoteSource() throws IOException {
        return Jsoup.connect("http://moduleok.hol.es/mock.html").get();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
