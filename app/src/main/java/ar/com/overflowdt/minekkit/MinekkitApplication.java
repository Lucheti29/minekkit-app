package ar.com.overflowdt.minekkit;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;


public class MinekkitApplication extends Application {

    public void onCreate() {
        Log.d("App", "onCreate");
        Parse.initialize(this, "ZENTAQLhrOXwye0ZqvNJRonB3Tx9kwDeGyPKxadL", "ZTEKcJlyc8SOcxNnDvEJXxYdCP1lBDF1hDC6aDxe");
    }
}
