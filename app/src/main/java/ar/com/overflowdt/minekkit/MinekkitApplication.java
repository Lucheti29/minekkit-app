package ar.com.overflowdt.minekkit;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;


import java.util.HashMap;

import ar.com.overflowdt.minekkit.models.Session;


public class MinekkitApplication extends Application {
    //Google Analytics
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "onCreate");
        Session.setContext(this);
        Parse.initialize(this, "ZENTAQLhrOXwye0ZqvNJRonB3Tx9kwDeGyPKxadL", "ZTEKcJlyc8SOcxNnDvEJXxYdCP1lBDF1hDC6aDxe");
    }

}
