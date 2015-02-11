package ua.samosfator.moduleok;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class App extends Application {

    private static Context mContext;
    private static Point screenSize;
    private static Date updateTime;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Preferences.init(getApplicationContext());
    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean hasInternetConnection() {
        final ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static Point getScreenSize() {
        if (screenSize == null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            screenSize = new Point();
            display.getSize(screenSize);
        }
        return screenSize;
    }

    public static void registerClassForEventBus(Object that) {
        if (!EventBus.getDefault().isRegistered(that)) {
            EventBus.getDefault().register(that);
        }
    }

    public static String getVersion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        return "";
    }

    public static String getFormattedUpdateTime() {
        String formatTemplate = "dd.MM.yy " + mContext.getString(R.string.date_hour_divider) + " kk:mm";
        DateFormat dateFormat = new SimpleDateFormat(formatTemplate, Locale.getDefault());
        if (updateTime == null) return "";
        return dateFormat.format(updateTime);
    }

    public static void setUpdateTime(Date updateTime) {
        App.updateTime = updateTime;
    }

    public static boolean isServiceRunning(Class<? extends Service> serviceClass) {
        ActivityManager manager = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public enum TrackerName {
        APP_TRACKER
    }

    Map<TrackerName, Tracker> mTrackers = new HashMap<>();

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.app_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }
}