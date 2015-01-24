package ar.com.overflowdt.minekkit;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;


public class MinekkitApplication extends Application {
    //Google Analytics
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
    
    public void onCreate() {
        Log.d("App", "onCreate");
        Parse.initialize(this, "ZENTAQLhrOXwye0ZqvNJRonB3Tx9kwDeGyPKxadL", "ZTEKcJlyc8SOcxNnDvEJXxYdCP1lBDF1hDC6aDxe");
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                    : null;
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
}
