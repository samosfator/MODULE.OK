package ua.samosfator.moduleok;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

class LoadPageAsyncTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
        Document mainPage = Jsoup.parse("<div id=\"content\"></div>");
        try {
//            mainPage = getFromLocalSource();
            mainPage = getFromRemoteSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainPage.select("#content").html();
    }

    private Document getFromRemoteSource() throws IOException {
        return Jsoup.connect("http://mod.tanet.edu.te.ua/index.php")
                .cookie("PHPSESSID", Preferences.read("SESSIONID", "")).get();
    }

    private Document getFromLocalSource() throws IOException {
        InputStream mockHtmlStream = App.getContext().getResources().openRawResource(R.raw.mock);
        String relativeBaseUri = "http://mod.tanet.edu.te.ua/ratings/index";
        return Jsoup.parse(mockHtmlStream, "UTF-8", relativeBaseUri);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
