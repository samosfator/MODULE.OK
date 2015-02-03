package ua.samosfator.moduleok;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        DateFormat dateFormat = new SimpleDateFormat("kk:mm   dd.MM.yy ", Locale.getDefault());
        if (updateTime == null) return "asdasdasdasdasd";
        return dateFormat.format(updateTime);
    }

    public static void setUpdateTime(Date updateTime) {
        App.updateTime = updateTime;
    }
}