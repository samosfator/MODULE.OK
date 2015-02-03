package ua.samosfator.moduleok;

import java.util.concurrent.ExecutionException;

public class PageLoader {

    private final static int MIN_VALID_PAGE_SIZE = 1400;

    public static String getMainPageHtml(boolean forceLoad) {
        if (!Auth.isLoggedIn()) {
            throw new IllegalArgumentException("MUST LOG IN AT FIRST");
        }
        return loadMainPage(forceLoad);
    }

    private static String loadMainPage(boolean forceLoad) {
        if (mainPageIsValid(getSavedMainPage()) && !forceLoad) return getSavedMainPage();

        String mainPageHtml = downloadMainPage();

        if (mainPageIsValid(mainPageHtml)) {
            Preferences.save("mainPageHtml", mainPageHtml);
            return mainPageHtml;
        } else {
            mainPageHtml = downloadMainPage();
            if (mainPageIsValid(mainPageHtml)) {
                Preferences.save("mainPageHtml", mainPageHtml);
                return mainPageHtml;
            } else {
                throw new SessionIdExpiredException();
            }
        }
    }

    private static boolean mainPageIsValid(final String pageHtml) {
        return pageHtml.length() > MIN_VALID_PAGE_SIZE;
    }

    private static String getSavedMainPage() {
        return Preferences.read("mainPageHtml", "");
    }

    private static String downloadMainPage() {
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
