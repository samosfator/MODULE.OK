package ua.samosfator.moduleok;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.event.LoadPageCompleteEvent;

public class LoadPageAsyncTask extends AsyncTask<Void, Void, String> {

    private boolean async;

    public LoadPageAsyncTask() {

    }

    public LoadPageAsyncTask(boolean async) {
        this.async = async;
    }

    @Override
    protected String doInBackground(Void... params) {
        Document mainPage = Jsoup.parse("<div id=\"content\"></div>");
        try {
//            mainPage = getFromCustomRemoteSource();
            mainPage = getFromRemoteSource();
            if (async) {
                EventBus.getDefault().post(new LoadPageCompleteEvent(mainPage.html()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainPage.html();
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
