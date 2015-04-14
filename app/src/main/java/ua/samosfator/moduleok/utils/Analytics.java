package ua.samosfator.moduleok.utils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

import ua.samosfator.moduleok.R;

public class Analytics {
    public static enum TrackerName {
        APP_TRACKER
    }

    private static Map<TrackerName, Tracker> mTrackers = new HashMap<>();

    public static synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(App.getContext());
            Tracker t = analytics.newTracker(R.xml.app_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }

    public static void trackFragmentView(String fragmentName) {
        Tracker tracker = Analytics.getTracker(Analytics.TrackerName.APP_TRACKER);
        tracker.setScreenName(fragmentName);
        tracker.enableAdvertisingIdCollection(true);
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    public static void trackEvent(String category, String action) {
        Tracker tracker = Analytics.getTracker(Analytics.TrackerName.APP_TRACKER);
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }
}
